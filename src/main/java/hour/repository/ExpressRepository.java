package hour.repository;

import hour.model.Express;
import hour.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpressRepository extends JpaRepository<Express,String> {
    Page<Express> findAllByPreorderIdAndAbledTrue(String preorder_id, Pageable pageable);

    Express findFirstByExpressId(String express_id);

    List<Express> findAllByPreorderId(String preorderid);

    Page<Express> findAllByExpressIdContaining(String value, Pageable pageable);

    Page<Express> findAllByUserIdContaining(String value, Pageable pageable);

    Page<Express> findAllByExpressPointId(String express_point_id,Pageable pageable);

    List<Express> findAllByAbledTrueAndStatusNotAndPayedAndUserId(int status,int payed,String user_id);

    @Query(value="SELECT\n" +
            "\ta.express_id AS express_id,\n" +
            "\ta.STATUS AS STATUS,\n" +
            "\ta.abled AS abled,\n" +
            "\tf.NAME AS express_point,\n" +
            "\ta.NAME AS NAME,\n" +
            "\ta.payed AS payed,\n" +
            "\ta.phone_num AS phone_num,\n" +
            "\ta.preorder_id AS preorder_id,\n" +
            "\ta.receive_code AS receive_code,\n" +
            "\te.value as send_method,\n" +
            "\ta.sender_admin_id AS sender_admin_id,\n" +
            "\td.size_name AS size_name,\n" +
            "\ta.sms_content AS sms_content,\n" +
            "\ta.time AS time,\n" +
            "\ta.total_fee AS total_fee,\n" +
            "\ta.user_id AS user_id,\n" +
            "\tCONCAT( c.NAME, b.room_num ) AS address \n" +
            "FROM\n" +
            "\tuser_express a\n" +
            "\tLEFT JOIN user_address b ON b.user_id = a.user_id\n" +
            "\tLEFT JOIN user_dest_building c ON b.build_id = c.id\n" +
            "\tLEFT JOIN user_express_size d ON a.size_id = d.size_id\n" +
            "\tLEFT JOIN user_send_method e ON a.send_method_id = e.id\n" +
            "\tLEFT JOIN user_express_point f ON a.express_point_id = f.express_point_id \n" +
            "WHERE\n" +
            "\ta.payed = :payed \n" +
            "ORDER BY\n" +
            "\ta.time DESC \n" +
            "\tLIMIT 0,\n" +
            "\t10;",nativeQuery = true)
    Page<Express> findAllByPayed(Pageable pageable, @Param("payed") int payed);


}
