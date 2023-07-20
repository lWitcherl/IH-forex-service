package ua.sikoraton.forexservice.specisications;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter @Getter
@NoArgsConstructor
public class SearchCriteria {
    private String key;
    private String value;
    private List<String> list;

    public SearchCriteria(String key) {
        this.key = key;
    }
}
