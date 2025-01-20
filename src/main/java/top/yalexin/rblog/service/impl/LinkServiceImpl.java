package top.yalexin.rblog.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.yalexin.rblog.entity.Link;
import top.yalexin.rblog.exception.DataFormatErrorException;
import top.yalexin.rblog.service.LinkService;
import top.yalexin.rblog.util.ParseJSONFileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LinkServiceImpl implements LinkService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public List<Link> getAllLink()  {

        List<Link> links = null;
        try {
            links = ParseJSONFileUtils.getJSONObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return links;
    }
}
