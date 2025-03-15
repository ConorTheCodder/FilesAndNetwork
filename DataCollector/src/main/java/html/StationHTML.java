package html;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StationHTML {
    String stationName;
    String lineNumber;
    Boolean hasConnection;
}
