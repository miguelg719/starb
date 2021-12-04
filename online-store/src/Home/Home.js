import { Fade } from "react-awesome-reveal";
import { Link } from "react-router-dom";
import './Home.css'

function Home() {
    return (
        <div className="home">
            <Fade>
                <div className="top">
                    <div className="banner_img">
                        <img
                            src='https://i.insider.com/58deae778508681a008b47b1?width=600&format=jpeg&auto=webp'
                            alt=''
                        />
                    </div>
                    <div className="banner_container">
                        <text className="banner1">
                            Welcome to our Starbucks Online Store
                        </text>
                        <text className="banner2">
                            Enjoy exploring our Menu, Rewards program, and Gift Cards
                        </text>
                        <text className="banner3">
                            ❤️ ☕️ ❤️
                        </text>
                    </div>
                </div>
            </Fade>
            <Fade>
                <div className="second">
                    <div className="second_container">
                        <text className="second1">
                            It's our Birthday!
                        </text>
                        <text className="second2">
                            This year, we wanted to celebrate it differently. Instead of asking for gifts, we're giving them away! Participate in our Starbucks bingo at any of our locations to win a $5 gift card
                        </text>
                    </div>
                    <div className="second_img">
                        <img src='https://globalassets.starbucks.com/assets/486e1febffa9433d9f95f75410af0386.jpg'
                            alt=''
                        />
                    </div>
                </div>
            </Fade>
            <Fade>
                <div className="third">
                    <text className="second1">
                        It's our Birthday!
                        </text>
                    <text className="second2">
                        This year, we wanted to celebrate it differently. Instead of asking for gifts, we're giving them away! Participate in our Starbucks bingo at any of our locations to win a $5 gift card
                        </text>
                </div>
            </Fade>
        </div >
    );
}

export default Home;