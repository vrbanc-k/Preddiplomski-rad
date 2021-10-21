package hr.fer.zavrsni.dao;

import hr.fer.zavrsni.model.PlacedOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PlacedOrderRepository extends JpaRepository<PlacedOrder,Long> {
        List<PlacedOrder> findByRestaurantId(Long id);

        PlacedOrder findById(Long id);

        @Modifying(clearAutomatically = true)
        @Transactional
        @Query("UPDATE PlacedOrder placedorder SET placedorder.isTaken=:istaken , placedorder.takenBy=:takenby WHERE placedorder.id=:orderId")
        void updateIsTaken(@Param("istaken")boolean isTaken, @Param("takenby")String takenBy,@Param("orderId")Long id);

        @Modifying(clearAutomatically = true)
        @Transactional
        @Query("UPDATE PlacedOrder placedorder SET placedorder.isCompleted=:iscompleted  WHERE placedorder.id=:orderId")
        void updateIsCompleted(@Param("iscompleted")boolean isCompleted,@Param("orderId")Long id);

        List<PlacedOrder> findByTakenBy(String takenBy);
}
