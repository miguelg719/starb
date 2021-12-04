import './App.css';
import React, { useEffect, Component } from "react";
import Home from './Home/Home.js';
import Header from './Home/Header/Header.js'
import MenuScreen from './Menu/MenuScreen.js';
import RewardsScreen from './Rewards/RewardsScreen.js';
import GiftScreen from './Gifts/GiftScreen.js';
import { Button } from 'react-bootstrap';
import Footer from './Home/Footer/Footer.js';

import {
  BrowserRouter as Router,
  Switch,
  Route,
  Redirect,
} from "react-router-dom";
import { ThemeConsumer } from 'react-bootstrap/esm/ThemeProvider';

class App extends Component {

  state = {
    isLoading: true,
    orders: []
  };

  async componentDidMount() {
    const response = await fetch('/api/orders');
    const body = await response.json();
    this.setState({ orders: body, isLoading: false });
  }

  async addOrder(id) {
    const requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ drink: "Capuccino", milk: "Whole", size: "Tall" })
    };
    const r = await fetch(`/api/order/register/${id}`, requestOptions);
    const body = await r.json();
    this.setState({ result: body, isLoading: false });
  }



  render() {

    const { orders, isLoading } = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    return (
      <div className="App">
        <Router>
          <Switch>
            <Route exact path="/">
              <Header></Header>
              {
                // orders.map(order =>
                // <div key={order.id}>
                //   {order.total}
                //   <Button className='item' onClick={() => this.addOrder(1)}>here</Button>
                // </div>
                // )
              }
              <Home></Home>
              <Footer></Footer>
            </Route>
            <Route exact path="/menu">
              <Header></Header>
              <MenuScreen />
              <Footer></Footer>
            </Route>
            <Route exact path="/gifts">
              <Header></Header>
              <GiftScreen />
              <Footer></Footer>
            </Route>
            <Route exact path="/rewards">
              <Header></Header>
              {
                //orders.map(order =>
                //<div key={order.id}>
                //  {order.total}
                //</div>
                //)
              }
              <RewardsScreen />
              <Footer></Footer>
            </Route>
          </Switch>
        </Router>
      </div>
    );
  }
}

export default App;
