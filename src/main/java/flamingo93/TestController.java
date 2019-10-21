package flamingo93;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("test")
    public Boolean amLeader() {
        System.out.println(LeaderEventListener.amLeader);
        return LeaderEventListener.amLeader;
    }
}
