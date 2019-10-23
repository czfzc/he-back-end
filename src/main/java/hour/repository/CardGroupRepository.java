package hour.repository;

import hour.model.CardGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardGroupRepository extends JpaRepository<CardGroup,String> {

    @Query(value = "SELECT\n" +
            "\tb.card_type_id AS card_type_id,\n" +
            "\ta.card_id AS card_id,\n" +
            "\tb.card_title AS card_title,\n" +
            "\tb.card_addition AS card_addition ,\n" +
            "\t'1' as owned\n" +
            "FROM\n" +
            "\tuser_card a\n" +
            "\tLEFT JOIN user_card_type b ON b.card_type_id = a.card_type_id \n" +
            "WHERE\n" +
            "\ta.user_id = :user_id \n" +
            "\tAND b.abled = 1 \n" +
            "\tAND b.showed = 1 \n" +
            "\tAND a.abled = 1 \n" +
            "\tAND date_format( a.end_date, '%Y-%m-%d-%H-%i-%S' ) > date_format( now(), '%Y-%m-%d-%H-%i-%S' ) UNION\n" +
            "SELECT\n" +
            "\tb.card_type_id AS card_type_id,\n" +
            "\tREPLACE ( uuid(), \"-\", \"\" ) AS card_id,\n" +
            "\tb.card_title AS card_title,\n" +
            "\tb.card_addition AS card_addition,\n" +
            "\t'0' as owned\n" +
            "FROM\n" +
            "\tuser_card_type b \n" +
            "WHERE\n" +
            "\tb.abled = 1 \n" +
            "\tAND b.showed = 1 \n" +
            "\tAND NOT EXISTS (\n" +
            "\tSELECT\n" +
            "\t\tcard_type_id \n" +
            "\tFROM\n" +
            "\t\tuser_card a \n" +
            "\tWHERE\n" +
            "\t\ta.card_type_id = b.card_type_id \n" +
            "\t\tAND user_id = :user_id \n" +
            "\t\tAND a.abled = 1 \n" +
            "\tAND date_format( a.end_date, '%Y-%m-%d-%H-%i-%S' ) > date_format( now(), '%Y-%m-%d-%H-%i-%S' ) \n" +
            "\t)",nativeQuery = true)
    List<CardGroup> findAll(@Param("user_id")String user_id);
}
