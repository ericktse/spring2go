package com.spring2go.common.rule.engine.reader;

import com.spring2go.common.rule.engine.entity.Rule;
import com.spring2go.common.rule.engine.exception.RuleEngineException;
import com.spring2go.common.rule.engine.util.RuleCacheUtils;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * XML规则配置加载器
 *
 * @author xiaobin
 */
public class XmlRuleReader extends AbstractRuleReader {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<Rule> itemList = null;

    @Override
    public List<Rule> loadRules() throws RuleEngineException {
        //缓存加载
        if (!RuleCacheUtils.RULECACHE.isEmpty()) {
            RuleCacheUtils.RULECACHE.clear();
        }
        itemList = new ArrayList<Rule>();

        try {
            // 创建DOM文档对象
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = dFactory.newDocumentBuilder();
            Document doc;

            //String configFile =PropertyUtil.getProperty("xml.rule.filename");
            String configFile = "D:\\workspace\\spring2go\\spring2go-common\\spring2go-common-rule-engine\\src\\main\\resources\\samplerule.xml";
//            if (!configFile.startsWith(File.separator)) {
//                File dir = new File(XmlRuleReader.class.getClassLoader().getResource("").getPath());
//                configFile = dir + File.separator + configFile;
//            }

            doc = builder.parse(new File(configFile));

            // 获取包含类名的文本节点
            NodeList nodeList = doc.getElementsByTagName("rule");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Rule item = new Rule();
                Node node = nodeList.item(i);
                NamedNodeMap attributes = node.getAttributes();
                if (null == attributes || attributes.getNamedItem("id") == null) {
                    logger.error("rule id must not be null. rule.context = {}", node.getTextContent());
                    return null;
                }

                for (int j = 0; j < attributes.getLength(); j++) {
                    Node attrNode = attributes.item(j);
                    item.set(attrNode.getNodeName(), attrNode.getNodeValue());
                }

                // TODO: 新增xml别名 alias of attribute name.

                //获取子节点配置
                if (node.hasChildNodes()) {
                    Node child = node.getFirstChild();
                    while (child != null) {

                        if ("property".equalsIgnoreCase(child.getNodeName())) {
                            NamedNodeMap childAttrs = child.getAttributes();
                            Node nameNode = childAttrs.getNamedItem("name");
                            Node valueNode = childAttrs.getNamedItem("value");
                            if (valueNode == null || nameNode == null) {
                                throw new RuleEngineException("rule format error, attribute value or name must existed.");
                            }

                            if ("whenSqlParam".equalsIgnoreCase(nameNode.getNodeValue())) {
                                Node typeNode = childAttrs.getNamedItem("type");
                                item.setWhenSqlParamValue(valueNode.getNodeValue());
                                item.setWhenSqlParamType(typeNode.getNodeValue());
                            } else if ("whenSqlCompare".equalsIgnoreCase(nameNode.getNodeValue())) {
                                Node operateNode = childAttrs.getNamedItem("operate");
                                item.setWhenSqlCompareOperate(operateNode.getNodeValue());
                                item.setWhenSqlCompareValue(valueNode.getNodeValue());
                            } else if ("thenSqlParam".equalsIgnoreCase(nameNode.getNodeValue())) {
                                Node typeNode = childAttrs.getNamedItem("type");
                                item.setThenSqlParamValue(valueNode.getNodeValue());
                                item.setThenSqlParamType(typeNode.getNodeValue());
                            } else {
                                item.set(nameNode.getNodeValue(), valueNode.getNodeValue());
                            }
                        }
                        child = child.getNextSibling();
                    }
                }

                //TODO:检查规则的格式是否正确
                itemList.add(item);
            }
        } catch (Exception e) {
            logger.error("xml rule load error :", e);
            throw new RuleEngineException(e.getCause());
        }

        //缓存
        synchronized (RuleCacheUtils.RULECACHE) {
            RuleCacheUtils.RULECACHE.addAll(itemList);
        }

        return RuleCacheUtils.RULECACHE;

    }

}
