package coding.task.shoppoints.domain.reward.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: Award Points Entity
 * @author: Ryan Mei
 * @date: 4/5/24
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RewardPointsEntity {
    private int lastMonth;
    private int twoMonthsAgo;
    private int threeMonthsAgo;
    private int total;
}
