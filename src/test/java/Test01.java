import org.joker.json.JsonArray;
import org.joker.json.JsonFacade;
import org.joker.json.JsonObject;

public class Test01 {

    public static void main(String[] args) {
        JsonObject jsonObject = JsonFacade.parseObject("   " +
                "    " +
                "{\"hello\": \"world\", \"key1\" : " +
                " \"v1\"   }");
        System.out.println(jsonObject);

        JsonArray jsonArray = JsonFacade.parseArray("   " +
                "    " +
                "{[\"hello\", \"world\", {\"key1\" : " +
                " \"v1\"}  ," +
                "  " +
                "123.1, 123 ] ");
        System.out.println(jsonArray);
    }
}
