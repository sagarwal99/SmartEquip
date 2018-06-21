package smart;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.Random;

@RestController
public class SmartEquipController {

    private String clientResponse = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<body>\n" +
            "\n" +
            "<h1>Please sum the number %s</h1>\n" +
            "\n" +
            "<form action=\"/sum\" method=\"post\">\n" +
            " Sum: <input type=\"text\" name=\"sumNbr\"><br>\n\n\n" +
            " <Label for=\"msg\">%s</Label><br>\n\n\n" +
            "  <input type=\"hidden\" id=\"numsId\" name=\"nums\" value=\"%s\">\n" +
            "  <input type=\"submit\" value=\"Submit\">\n" +
            "</form>\n" +
            "\n" +
            "</body>\n" +
            "</html>";

    private String successResponse = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<body>\n" +
            "\n" +
            "<h1>Success</h1>\n" +
            "\n" +
            "</body>\n" +
            "</html>";

    @RequestMapping(value="/", method= RequestMethod.GET )
    public String getRequest(Map<String, Object> model) {
        return getNewChallenge("");
    }


    @RequestMapping(value="/sum", method= RequestMethod.POST, consumes = org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String index(@RequestBody MultiValueMap<String, String> model) {
        String numStr = model.getFirst("nums");
        String sumStr = model.getFirst("sumNbr");
        String[] nums = numStr.split(",");
        if (!sumStr.trim().matches("[0-9]+")) {
            getNewChallenge("Enter Numeric Sum");
        } else {
            int computedSum = 0;
            for (String sn : nums) {
                computedSum+= Integer.parseInt(sn);
            }
            int sum = Integer.parseInt(sumStr);
            if (sum == computedSum) {
                return successResponse;
            } else {
                return getNewChallenge("Incorrect Sum. Please try again");
            }
        }
        return getNewChallenge("");
    }

    private String getNewChallenge(String msg) {
        String[] randomNum = new String[3];
        Random rg = new Random();
        for (int i = 0; i < 3; i++) {
            int num = rg.nextInt(10) + 1;
            randomNum[i] = Integer.toString(num);
        }
        String s = String.join(",", randomNum);
        return String.format(clientResponse, s, msg,  s);
    }
    
}
