package cn.jiayeli.es;

import cn.jiayeli.aop.exceptionHandle.model.ExceptionInfoModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

public class EsTest {
    public static void main(String[] args) throws IOException {
        /*//创建客户端
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));


        // 关闭客户端连接
        client.close();*/
        esTest_Client_insert();

    }

    public static void esTest_Client_insert() throws IOException {
        //创建客户端
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http")));

        //插入数据
        IndexRequest request = new IndexRequest();
        request.index("application_0000001").id("10003");

        ExceptionInfoModel model = new ExceptionInfoModel();
        model.setId("10001");

        //像ES插入数据必须将数据转化为JSON格式
        ObjectMapper mapper = new ObjectMapper();
        String modelJsonStr = mapper.writeValueAsString(model);
        request.source(modelJsonStr, XContentType.JSON);

        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        System.out.println(response.getResult());
        // 关闭客户端连接
        client.close();
    }

}
