import './Order.css'
import { Fade } from 'react-awesome-reveal'
import { Button } from 'react-bootstrap';
import React, { useEffect, Component } from "react";
import { Link } from "react-router-dom";



class Order extends Component {

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

        return (
            <Fade>
                <Link className='order' onClick={() => this.addOrder(1)}>
                    <img src={this.props.image} alt='' className='order_image' />
                    <h4 className='order_type'>{this.props.type}</h4>
                </Link>
            
            </Fade>
        );
    }
}

export default Order