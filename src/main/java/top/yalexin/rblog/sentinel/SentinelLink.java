package top.yalexin.rblog.sentinel;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class SentinelLink {

    private static final Logger logger = LoggerFactory.getLogger(SentinelLink.class);

    public static ResponseEntity getLinksBlockHandler(BlockException blex){
        logger.warn("限流了");
        return new ResponseEntity(null, HttpStatus.TOO_MANY_REQUESTS);
    }
}
