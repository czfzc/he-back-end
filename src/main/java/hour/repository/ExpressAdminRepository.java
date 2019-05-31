package hour.repository;

import com.alibaba.fastjson.JSONObject;
import hour.model.Express;
import hour.model.ExpressAdmin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpressAdminRepository extends JpaRepository<ExpressAdmin,String> {
    @Query(value="SELECT\n" +
            "\ta.express_id AS express_id,\n" +
            "\tany_value(a.STATUS) AS status,\n" +
            "\tany_value(a.abled) AS abled,\n" +
            "\tany_value(f.name) AS express_point,\n" +
            "\tany_value(a.name) AS name,\n" +
            "\tany_value(a.payed) AS payed,\n" +
            "\tany_value(a.phone_num) AS phone_num,\n" +
            "\tany_value(a.preorder_id)AS preorder_id,\n" +
            "\tany_value(a.receive_code) AS receive_code,\n" +
            "\tany_value(e.value) as send_method,\n" +
            "\tany_value(a.sender_admin_id) AS sender_admin_id,\n" +
            "\tany_value(d.size_name) AS size_name,\n" +
            "\tany_value(a.sms_content) AS sms_content,\n" +
            "\tany_value(a.time) AS time,\n" +
            "\tany_value(a.total_fee) AS total_fee,\n" +
            "\tany_value(a.user_id) AS user_id,\n" +
            "\tany_value(CONCAT( c.NAME, b.room_num )) AS address \n" +
            "FROM\n" +
            "\tuser_express a\n" +
            "\tLEFT JOIN user_address b ON b.user_id = a.user_id\n" +
            "\tLEFT JOIN user_dest_building c ON b.build_id = c.id\n" +
            "\tLEFT JOIN user_express_size d ON a.size_id = d.size_id\n" +
            "\tLEFT JOIN user_send_method e ON a.send_method_id = e.id\n" +
            "\tLEFT JOIN user_express_point f ON a.express_point_id = f.express_point_id \n" +
            "\tWHERE\n" +
            "\ta.payed = :payed\n" +
            "\tgroup by a.express_id\n" +
            " order by time desc limit :min,:size ",nativeQuery = true)
    List<JSONObject> findAllByPayed(@Param("min")int min,
                                    @Param("size")int size, @Param("payed") int payed);

    @Query(value="SELECT\n" +
            "\ta.express_id AS express_id,\n" +
            "\tany_value(a.STATUS) AS status,\n" +
            "\tany_value(a.abled) AS abled,\n" +
            "\tany_value(f.name) AS express_point,\n" +
            "\tany_value(a.name) AS name,\n" +
            "\tany_value(a.payed) AS payed,\n" +
            "\tany_value(a.phone_num) AS phone_num,\n" +
            "\tany_value(a.preorder_id)AS preorder_id,\n" +
            "\tany_value(a.receive_code) AS receive_code,\n" +
            "\tany_value(e.value) as send_method,\n" +
            "\tany_value(a.sender_admin_id) AS sender_admin_id,\n" +
            "\tany_value(d.size_name) AS size_name,\n" +
            "\tany_value(a.sms_content) AS sms_content,\n" +
            "\tany_value(a.time) AS time,\n" +
            "\tany_value(a.total_fee) AS total_fee,\n" +
            "\tany_value(a.user_id) AS user_id,\n" +
            "\tany_value(CONCAT( c.NAME, b.room_num )) AS address \n" +
            "FROM\n" +
            "\tuser_express a\n" +
            "\tLEFT JOIN user_address b ON b.user_id = a.user_id\n" +
            "\tLEFT JOIN user_dest_building c ON b.build_id = c.id\n" +
            "\tLEFT JOIN user_express_size d ON a.size_id = d.size_id\n" +
            "\tLEFT JOIN user_send_method e ON a.send_method_id = e.id\n" +
            "\tLEFT JOIN user_express_point f ON a.express_point_id = f.express_point_id \n" +
            "\tWHERE\n" +
            "\ta.payed = :payed\n and" +
            "\ta.express_point_id = :express_point_id\n" +
            "\tgroup by a.express_id\n" +
            " order by time desc limit :min,:size ",nativeQuery = true)
    List<JSONObject> findAllByPointAndPayed(@Param("min")int min,
                                    @Param("size")int size, @Param("payed") int payed,
                                            @Param("express_point_id")String express_point_id);
}
