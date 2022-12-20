package cn.itcast.order.web;

import cn.itcast.feign.common.MyPage;
import cn.itcast.feign.common.OrderStatus;
import cn.itcast.feign.common.PageSearchData;
import cn.itcast.feign.common.Result;
import cn.itcast.feign.util.MyPageTool;
import cn.itcast.order.common.DeliverData;
import cn.itcast.order.common.OrderToSee;
import cn.itcast.order.domain.Order;
import cn.itcast.order.service.GoodService;
import cn.itcast.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shop")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;


    @PostMapping("/adminSearchToPaidOrder")
    public Result adminSearchToPaidOrder(@RequestBody PageSearchData pageSearchData) {
        List<Order> orders = orderService.findOrderByStatus( OrderStatus.NotPaid);
        List<OrderToSee> orderToSees = OrderToSee.getToSee(orders);
        MyPage<OrderToSee> toSeeMyPage = MyPageTool.getPage(orderToSees,pageSearchData.getPageSize(),pageSearchData.getPageNum());
        return Result.succ(toSeeMyPage);
    }

    @PostMapping("/adminSearchUndeliverOrder")
    public Result adminSearchUndeliverOrder(@RequestBody PageSearchData pageSearchData) {
        List<Order> orders = orderService.findOrderByStatus(OrderStatus.NotYetShipped);
        List<OrderToSee> orderToSees = OrderToSee.getToSee(orders);
        MyPage<OrderToSee> toSeeMyPage = MyPageTool.getPage(orderToSees,pageSearchData.getPageSize(),pageSearchData.getPageNum());
        return Result.succ(toSeeMyPage);
    }



    @PostMapping("/adminSearchUnreceiveOrder")
    public Result adminSearchUnreceiveOrder(@RequestBody PageSearchData pageSearchData) {
        List<Order> orders = orderService.findOrderByStatus(OrderStatus.Shipped);
        List<OrderToSee> orderToSees = OrderToSee.getToSee(orders);
        MyPage<OrderToSee> toSeeMyPage = MyPageTool.getPage(orderToSees,pageSearchData.getPageSize(),pageSearchData.getPageNum());
        return Result.succ(toSeeMyPage);
    }

    @PostMapping("/adminSearchFinishOrder")
    public Result adminSearchFinishOrder(@RequestBody PageSearchData pageSearchData) {
        List<Order> orders = orderService.findOrderByStatus(OrderStatus.Received);
        List<OrderToSee> orderToSees = OrderToSee.getToSee(orders);
        MyPage<OrderToSee> toSeeMyPage = MyPageTool.getPage(orderToSees,pageSearchData.getPageSize(),pageSearchData.getPageNum());
        return Result.succ(toSeeMyPage);
    }

    @PostMapping("/deliver")
    public Result deliver(@RequestBody DeliverData deliverData) {
        try{
            orderService.deliverGoodsOfOrder(deliverData.getOrderID(),deliverData.getDeliverNo());
            return Result.succ(null,"发货成功");
        }catch (Exception e){
            e.printStackTrace();
            return Result.fail(340,"发货失败\n"+e.getMessage());
        }
    }
}
