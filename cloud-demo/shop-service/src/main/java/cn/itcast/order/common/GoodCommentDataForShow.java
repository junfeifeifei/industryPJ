package cn.itcast.order.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodCommentDataForShow {
    String userID;
    String content;
    Integer stars;
}
