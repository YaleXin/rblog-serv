package top.yalexin.rblog.config;

import com.alibaba.csp.sentinel.datasource.*;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.transport.util.WritableDataSourceRegistry;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.stereotype.Component;
import top.yalexin.rblog.constant.SentinelConstant;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Arrays;
import java.util.List;

@Component
public class SentinelConfig {

    @PostConstruct
    public void initRules() throws Exception {
        initFlowRules();
        initDegradeRules();
        initLocalDataSource();
    }

    /**
     * 初始化限流规则
     */
    public void initFlowRules() {
        // 单 IP 尝试访问的接口限流
        int[][] array = {
                {1, 2},  //
                {2, 3}
        };

        ParamFlowRule adminLoginRule = new ParamFlowRule(SentinelConstant.ADMIN_LOGIN_RULE)
                .setParamIdx(0) // 对第 0 个参数限流，即 IP 地址
                .setCount(3) // 每分钟最多 3 次
                .setDurationInSec(60); // 规则的统计周期为 60 秒

        ParamFlowRule adminGetPoWConfig = new ParamFlowRule(SentinelConstant.ADMIN_POW_RULE)
                .setParamIdx(0) // 对第 0 个参数限流，即 IP 地址
                .setCount(10) // 每分钟最多 10 次
                .setDurationInSec(60); // 规则的统计周期为 60 秒

        ParamFlowRule adminVerifyPoW = new ParamFlowRule(SentinelConstant.ADMIN_POW_VERIFY_RULE)
                .setParamIdx(0) // 对第 0 个参数限流，即 IP 地址
                .setCount(5) // 每分钟最多 10 次
                .setDurationInSec(60); // 规则的统计周期为 60 秒

        //
        ParamFlowRuleManager.loadRules(Arrays.asList(adminLoginRule, adminGetPoWConfig, adminVerifyPoW));
    }

    /**
     * 初始化降级规则
     */
    public void initDegradeRules() {
        // 熔断规则
    }

    /**
     * 持久化配置为本地文件
     */
    public void initLocalDataSource() throws Exception {
        // 获取项目根目录
        String rootPath = System.getProperty("user.dir");
        // sentinel 目录路径
        File sentinelDir = new File(rootPath, "sentinel");
        // 目录不存在则创建
        if (!sentinelDir.exists()) {
            sentinelDir.mkdirs();
        }
        // 规则文件路径
        String flowRulePath = new File(sentinelDir, "FlowRule.json").getAbsolutePath();
        String degradeRulePath = new File(sentinelDir, "DegradeRule.json").getAbsolutePath();

        // Data source for FlowRule
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new FileRefreshableDataSource<>(flowRulePath, flowRuleListParser);
        // Register to flow rule manager.
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
        WritableDataSource<List<FlowRule>> flowWds = new FileWritableDataSource<>(flowRulePath, this::encodeJson);
        // Register to writable data source registry so that rules can be updated to file
        WritableDataSourceRegistry.registerFlowDataSource(flowWds);

        // Data source for DegradeRule
        FileRefreshableDataSource<List<DegradeRule>> degradeRuleDataSource
                = new FileRefreshableDataSource<>(
                degradeRulePath, degradeRuleListParser);
        DegradeRuleManager.register2Property(degradeRuleDataSource.getProperty());
        WritableDataSource<List<DegradeRule>> degradeWds = new FileWritableDataSource<>(degradeRulePath, this::encodeJson);
        // Register to writable data source registry so that rules can be updated to file
        WritableDataSourceRegistry.registerDegradeDataSource(degradeWds);
    }

    private Converter<String, List<FlowRule>> flowRuleListParser = source -> JSON.parseObject(source,
            new TypeReference<List<FlowRule>>() {
            });
    private Converter<String, List<DegradeRule>> degradeRuleListParser = source -> JSON.parseObject(source,
            new TypeReference<List<DegradeRule>>() {
            });

    private <T> String encodeJson(T t) {
        return JSON.toJSONString(t);
    }
}
