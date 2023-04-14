// 查询订单量
const ordersCount = (params) => {
    return $axios({
        url: '/order/ordersCount',
        method: 'get',
        params
    })
}
