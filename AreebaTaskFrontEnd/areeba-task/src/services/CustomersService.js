import http from "../http-common";

class CustomersService {

  getAll() {
    return http.get(`/getAllCustomers`);
  }

  addCustomer(user){
    return http.post(`/addCustomer?name=${user.name}&phone=${user.phoneNumber}&address=${user.address}`,user)
  }

  editUser(user){
    return http.put(`/updateById?id=${user.id}&name=${user.name}&phone=${user.phoneNumber}&address=${user.address}`)
  }

  deleteCustomer(user_id){
    return http.delete(`deleteById/${user_id}`)
  }

}

export default new CustomersService();