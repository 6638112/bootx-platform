package cn.bootx.common.sequence.range.jdbc;

import cn.bootx.common.mybatisplus.impl.BaseManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * 队列区间
 *
 * @author xxm
 * @date 2021/12/14
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class SequenceRangeManager extends BaseManager<SequenceRangeMapper, SequenceRange> {

}
