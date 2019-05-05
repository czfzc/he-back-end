package hour.service;

import hour.model.Order;
import hour.model.Refund;
import hour.repository.RefundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Iterator;
import java.util.List;

@Service("RefundService")
public class RefundServiceImpl implements RefundService{
    /**
     * 获取总订单数
     * @return
     */
    @Autowired
    EntityManager entityManager;

    @Autowired
    RefundRepository refundRepository;

    @Override
    public Long getCount(){
        String sql="select count(a.orderId) from Refund a";
        Query query = entityManager.createQuery(sql);
        return (Long)query.getSingleResult();
    }

    @Override
    public List<Refund> getRefund(int page, int size){
        Pageable pageable=new PageRequest(page, size, Sort.Direction.DESC, "time");
        Page<Refund> refunds=refundRepository.findAll(pageable);
        return refunds.getContent();
    }
}
