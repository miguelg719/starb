import itemList from './itemList.json';
import { Link } from 'react-router-dom';
import Item from './Item.js';
import Order from './Order.js';
import './ItemList.css';
import ArrowForwardIos from '@material-ui/icons/ArrowForwardIos';
import ArrowBackIos from '@material-ui/icons/ArrowBackIos';


function ItemList() {
    return (
        <div className="item_list">
            <div className="container">
                <h1>Menu</h1>
            </div>
            <div className="title">
                <h2>Coffee</h2>
            </div>
            <div className="wrapper">
                <ArrowBackIos className="arrow" />
                <div className="category">
                    <div className='item'>
                        {itemList.map((itemCategory) =>
                            itemCategory.drinks.map(({ type, path, image }) => (
                                <Order type={type} image={image} />
                            ))
                        )}
                    </div>
                </div>
                <ArrowForwardIos className="arrow" />
            </div>

            <div className="title">
                <h2>Food</h2>
            </div>
            <div className="wrapper">
                <ArrowBackIos className="arrow" />
                <div className="category">
                    <div className='item'>
                        {itemList.map((itemCategory) =>
                            itemCategory.food.map(({ type, path, image }) => (
                                <Item type={type} image={image} />
                            ))
                        )}
                    </div>
                </div>
                <ArrowForwardIos className="arrow" />
            </div>

            <div className="title">
                <h2>At Home Coffee</h2>
            </div>
            <div className="wrapper">
                <span className="arrow" />
                <div className="category">
                    <div className='item'>
                        {itemList.map((itemCategory) =>
                            itemCategory.atHomeCoffee.map(({ type, path, image }) => (
                                <Item type={type} image={image} />
                            ))
                        )}
                    </div>
                </div>
                <span className="arrow" />
            </div>

            <div className="title">
                <h2>Merchandise</h2>
            </div>
            <div className="wrapper">
                <span className="arrow"/>
                <div className="category">
                    <div className='item'>
                        {itemList.map((itemCategory) =>
                            itemCategory.merchandise.map(({ type, path, image }) => (
                                <Item type={type} image={image} />
                            ))
                        )}
                    </div>
                </div>
                <span className="arrow"/>
            </div>
        </div>
    );
}

export default ItemList