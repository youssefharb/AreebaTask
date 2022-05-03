import React, { useState, useEffect } from "react";
import CustomersService from "../services/CustomersService";

import { Container, Row, Form, FormGroup, FormControl, FormLabel, Button, Alert, Table } from 'react-bootstrap';

let setState = {
    name: "",
    address: "",
    phone:"",
    records: [],
    showAlert: false,
    alertMsg: "",
    alertType: "success",
    id: "",
    update: false,
};


const CustomersList = () => {
    const [customers, setCustomers] = useState([]);

    useEffect(() => {
        retrieveCustomers();
    }, []);
    let initialCustomerId = ""
    let initialUserName = "";
    let initialUserAddress = "";
    let initialUserNumber = "";
    let initilCode = "";
    let initialResponse="";

    const [userName, setUserName] = useState(initialUserName);
    const [userAddress, setUserAddress] = useState(initialUserAddress);
    const [number, setNumber] = useState(initialUserNumber);
    const [customerId, setCustomerId] = useState(initialCustomerId);
    const [code, setCode] = useState(initilCode);
    const [response, setResponse] = useState(initialResponse);

    
    const handleNameChange = event => {
        setUserName(event.target.value);
    };

    const handleAddressChange = event => {
        setUserAddress(event.target.value);
    };

    const handlePhoneChange = event => {
        setNumber(event.target.value);
    };

 const retrieveCustomers = () => {
     // code for getting all customers
     CustomersService.getAll()
            .then(response => {
                console.log(response.data);
                setCustomers(response.data);

            })
            .catch(e => {
                console.log(e);
            });
            
 };
 const deleteCustomer= (e) => {
    CustomersService.deleteCustomer(e.target.value)
    .then(response => {
      
        if(response.data.code===11){
           
            setState.name= "";
            setState.address="";
            setState.phone= "";
            setState.showAlert= true;
            setState.alertMsg=response.data.description;
            setState.alertType="success";
           
        }else   {
            
                
            setState.showAlert= true;
            setState.alertMsg=response.data.description;
            setState.alertType= "danger";
            
        }
        refreshList();
        
    })
    .catch(err => {
      console.log(err);
    });
}
const addCustomer= () => {
    var userInfo = {
        name: userName,
        address: userAddress,
        phoneNumber: number
    }

    CustomersService.addCustomer(userInfo)
    .then(response => {
        if(response.data.code===2){
           
            setState.name= "";
            setState.address="";
            setState.phone= "";
            setState.showAlert= true;
            setState.alertMsg=response.data.description;
            setState.alertType="success";
           
        }else   {
            
                
            setState.showAlert= true;
            setState.alertMsg=response.data.description;
            setState.alertType= "danger";
            
        }
        refreshList();
        
    })
    .catch(err => {
      console.log(err);
    });
}
const editRecord = (id,name,address,phone) => {
    
    setCustomerId(id);
    setUserName(name)
    setUserAddress(address);
    setNumber(phone);
    setState.update=true;
    refreshList();
}

const updateRecord =()=>{
    var userInfo = {
        id:customerId,
        name: userName,
        address: userAddress,
        phoneNumber: number
    }

    CustomersService.editUser(userInfo)
    .then(response => {
       if(response.data.code===2){
           
            setState.showAlert= true;
            setState.alertMsg=response.data.description;
            setState.alertType="success";
           
        }else   {
            
                
            setState.showAlert= true;
            setState.alertMsg=response.data.description;
            setState.alertType= "danger";
            
        }
        setState.update=false;
  
        refreshList();
        
    })
    .catch(err => {
      console.log(err);
    });

}
const refreshList = () => {
    retrieveCustomers();
    };

return(
    <div>
				<Container>
    <Row>
    {setState.showAlert === true ? (
						<Alert
							variant={setState.alertType}
							onClose={() => {
								setState.showAlert=false;
							}}
							dismissible
						>
							<Alert.Heading>{setState.alertMsg}</Alert.Heading>
						</Alert>
					) : null}
						<Table striped bordered hover size="sm">
							<thead>
								<tr>
									<th>id</th>
									<th>Name</th>
									<th>address</th>
                  <th>phone number</th>
									<th colSpan="2">Actions</th>
								</tr>
							</thead>
							<tbody>
								{customers?.map((Customer) => {
									return (
										<tr>
											<td>{Customer.id}</td>
											<td>{Customer.name}</td>
											<td>{Customer.address}</td>
                      <td>{Customer.phone}</td>

											<td>
												<Button variant="info" onClick={() =>editRecord(Customer.id,Customer.name,Customer.address,Customer.phone)}>
													Edit
												</Button>
											</td>
											<td>
												<Button variant="danger"
                                                value={Customer.id}
                                            onClick={deleteCustomer}>
													Delete
												</Button>
											</td>
										</tr>
									);
								})}
							</tbody>
						</Table>
					</Row>
                    <Row>
						<Form>
							<FormGroup>
								<FormLabel>Enter the name</FormLabel>
								<FormControl type="text" name="name" placeholder="Enter the name" onChange={handleNameChange} value={userName}></FormControl>
							</FormGroup>
							<FormGroup>
								<FormLabel>Enter the address</FormLabel>
								<FormControl type="text" name="address" value={userAddress} onChange={handleAddressChange} placeholder="Enter the address"></FormControl>
							</FormGroup>
              <FormGroup>
              <FormLabel>Enter the phone number (with country code ex :961)</FormLabel>
              <FormControl type="text" name="phone" value={number} onChange={handlePhoneChange} placeholder="Enter the phone number" ></FormControl>
            </FormGroup>
							{setState.update === true ? <Button  onClick={updateRecord}>update</Button> : <Button onClick={addCustomer}>Save</Button>}
						</Form>
					</Row>
                    </Container>
                    </div>

);

};

export default CustomersList;