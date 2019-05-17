package hour.repository;

import hour.model.Voucher;
import hour.model.VoucherGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoucherGroupRepository extends JpaRepository<VoucherGroup,Integer> {
    @Query(value = "SELECT\n" +
            "\tcount( a.type_id ) as count,\n" +
            "\ta.type_id,\n" +
            "\tany_value ( a.check_user_id ) as check_user_id,\n" +
            "\tany_value ( a.service_id ) as service_id,\n" +
            "\tany_value(b.name) as name\n" +
            "FROM\n" +
            "\tuser_voucher  a\n" +
            "\tINNER JOIN user_voucher_type b\n" +
            "\ton b.type_id=a.type_id\n" +
            "\tWHERE a.check_user_id =:user_id and a.abled=1 and a.used=0\n" +
            "GROUP BY\n" +
            "\ttype_id",nativeQuery = true)
    List<VoucherGroup> list(@Param("user_id")String user_id);
}
