package com.example.asm03spring.dao;
import com.example.asm03spring.entity.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ClinicRepository extends JpaRepository<Clinic, Integer> {

    @Query(value = "SELECT \n" +
            "    cli.name, cli.address, cli.phone, cli.time_working, cli.description, COUNT(du.id) AS total_doctor\n" +
            "FROM\n" +
            "    clinic cli\n" +
            "        JOIN\n" +
            "    doctor_user du ON cli.id = du.clinic_id\n" +
            "GROUP BY cli.id\n" +
            "ORDER BY total_doctor DESC\n" +
            "LIMIT 3;", nativeQuery = true)
    List<Object> listTop3ProminentClinic();

    @Query(value = "SELECT cli.* FROM clinic cli WHERE address LIKE %:data%", nativeQuery = true)
    List<Clinic> findClinicsByAddressKeyword(@Param("data") String keyword);

    @Query(value = "SELECT cli.* FROM clinic cli WHERE price LIKE %:data%", nativeQuery = true)
    List<Clinic> findClinicsByPriceKeyword(@Param("data") String keyword);

    @Query(value = "SELECT cli.* FROM clinic cli WHERE name LIKE %:data%", nativeQuery = true)
    List<Clinic> findClinicsByClinicNameKeyword(@Param("data") String keyword);

    @Query(value = "SELECT c.* FROM clinic c " +
            "JOIN category cat ON c.category_id = cat.id " +
            "WHERE cat.name LIKE %:keyword%", nativeQuery = true)
    List<Clinic> findClinicsByCategoryKeyword(@Param("keyword") String keyword);

}

