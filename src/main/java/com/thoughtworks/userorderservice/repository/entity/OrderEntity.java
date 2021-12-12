package com.thoughtworks.userorderservice.repository.entity;

import com.thoughtworks.userorderservice.dto.OrderDetail;
import com.thoughtworks.userorderservice.dto.OrderStatus;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(name = "t_order")
@TypeDef(name = "json", typeClass = JsonStringType.class)
public class OrderEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Type(type = "json")
    @Column(columnDefinition = "json" )
    private List<OrderDetail> orderDetails;
    private Integer totalPrice;
    private Integer deduction;
    private OrderStatus orderStatus;
    private Integer userid;
    private Integer merchantId;
    @CreatedDate
    private Long createTime;
    @LastModifiedDate
    private Long updateTime;
}
