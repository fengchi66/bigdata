package demo.utils;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.contrib.streaming.state.ConfigurableOptionsFactory;
import org.apache.flink.contrib.streaming.state.OptionsFactory;
import org.apache.flink.contrib.streaming.state.RocksDBNativeMetricOptions;

import org.rocksdb.*;

/**
 * @author wufc
 * @create 2020-04-06 3:36 下午
 */
public class MyOptionFactory implements ConfigurableOptionsFactory {
    private static final long DEFAULT_SIZE = 256 * 1024 * 1024;  // 256 MB
    private long blockCacheSize = DEFAULT_SIZE;

    @Override
    public OptionsFactory configure(Configuration configuration) {
        this.blockCacheSize =
                configuration.getLong("my.custom.rocksdb.block.cache.size", DEFAULT_SIZE);
        return this;
    }

    @Override
    public DBOptions createDBOptions(DBOptions currentOptions) {
        return currentOptions.setIncreaseParallelism(4)
                .setUseFsync(false)
                .setMaxBackgroundFlushes(1); // 后台最多同时进行的 Flush 任务数
    }

    @Override
    public ColumnFamilyOptions createColumnOptions(ColumnFamilyOptions currentOptions) {
        ColumnFamilyOptions columnFamilyOptions = currentOptions.setTableFormatConfig(
                new BlockBasedTableConfig()
                        .setBlockCacheSize(blockCacheSize)  // 设置读缓存大小
                        .setBlockSize(128 * 1024)  // 128 KB,设置块大小
                        .setCacheIndexAndFilterBlocks(true)  // 适合数据读热点，一般不打开  允许把这些索引和过滤器放到 Block Cache 中备用，这样可以提升局部数据存取的效率
                        .setPinL0FilterAndIndexBlocksInCache(true) // 适合数据读热点，一般不打开
        );

        columnFamilyOptions.setOptimizeFiltersForHits(true);  // 适合数据读热点，一般不打开  如果设置为 true，则 RocksDB 不会给 L0 生成 Bloom Filter
        columnFamilyOptions.setMinWriteBufferNumberToMerge(3);  // Write Buffer 合并的最小阈值，默认值为 1
        columnFamilyOptions.setTargetFileSizeBase(32 * 1024 * 1024); // 上一级的 SST 文件达到多大时触发 Compaction 操作，默认值是 2MB
        columnFamilyOptions.setCompactionStyle(CompactionStyle.FIFO);// 允许用户调整 Compaction 的组织方式，默认值是 LEVEL（较为均衡），但也可以改为 UNIVERSAL 或 FIFO.
//        columnFamilyOptions.setCompressionType(CompressionType.SNAPPY_COMPRESSION);// 指定对 Block 的压缩算法。

        return columnFamilyOptions;
    }

}
