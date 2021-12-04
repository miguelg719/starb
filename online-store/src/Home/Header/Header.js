import './Header.css';
import SignUpButton from './SignUpButton.js';
import SignInButton from './SignInButton.js';
import FindAStore from './FindAStore';
import { Link } from 'react-router-dom'


function Header() {
    return (
        <div className="header">
            <div className="header_first">
                <Link className="header_logo" to="/">
                    <img
                        src='https://upload.wikimedia.org/wikipedia/en/thumb/d/d3/Starbucks_Corporation_Logo_2011.svg/1200px-Starbucks_Corporation_Logo_2011.svg.png'
                        alt=''
                    />
                </Link>
                <Link className="header_link" to="/menu">Menu</Link>
                <Link className="header_link" to="/rewards">Rewards</Link>
                <Link className="header_link" to="/gifts">Gift Cards</Link>
            </div>
            <div className="header_last">
                <FindAStore />
                <SignInButton />
                <SignUpButton />
            </div>
        </div>
    );
}

export default Header