import React from "react";
import { Container, Row, Form, FormGroup, FormControl, FormLabel, Button, Alert, Table } from 'react-bootstrap';
import axios from 'axios';
import CustomerList from "./Component/CustomerList";
class App extends React.Component {

	render() {
		return (
		  <div>
			<CustomerList/>
			
		  </div>
		);
	  }
	}
	 
	export default App;