import { Link } from 'react-router-dom'
import './Item.css'
import { Fade } from 'react-awesome-reveal'

function Item({ type, image }) {
  return (
    <Fade>
      <Link className='item'>
        <img src={image} alt='' className='item_image' />
        <h4 className='item_type'>{type}</h4>
      </Link>
    </Fade>
  )
}

export default Item