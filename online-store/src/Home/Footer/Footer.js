import { Link } from 'react-router-dom';
import './Footer.css';

function Footer() {
    return (
        <div className="footer">
            <div className="footer_container">
                <div className="info">
                    <div className="one">
                        <Link className="link_header">About Us</Link>
                        <Link className="link">Our Heritage</Link>
                        <Link className="link">Our Coffee</Link>
                        <Link className="link">Our Team</Link>
                    </div>
                    <div className="two">
                        <Link className="link_header">Careers</Link>
                        <Link className="link">Culture and Values</Link>
                        <Link className="link">Inclusion, Diversity, and Equity</Link>
                        <Link className="link">U.S Careers</Link>
                    </div>
                    <div className="three">
                        <Link className="link_header">Social Impact</Link>
                        <Link className="link">Ethical Sourcing</Link>
                        <Link className="link">Leading in Sustainability</Link>
                        <Link className="link">Strenghtening Communities</Link>
                    </div>
                    <div className="four"></div>
                </div>
                <p className="copyright">© 2021 Starbucks</p>
            </div>
        </div>
    );
}

export default Footer