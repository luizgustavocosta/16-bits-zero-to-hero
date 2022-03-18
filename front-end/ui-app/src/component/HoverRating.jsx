import * as React from 'react';
import {Component} from 'react';
import Rating from '@mui/material/Rating';
import Box from '@mui/material/Box';
import StarIcon from '@mui/icons-material/Star';

class HoverRating extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        const labels = {
            0.5: 'Useless',
            1: 'Useless+',
            1.5: 'Poor',
            2: 'Poor+',
            2.5: 'Ok',
            3: 'Ok+',
            3.5: 'Good',
            4: 'Good+',
            4.5: 'Excellent',
            5: 'Excellent+',
        };
        const hover = -1;
        return (
          <Box
            sx={{
                width: 200,
                display: 'flex',
                alignItems: 'center',
            }}
          >
              <Rating
                name="hover-feedback"
                value={this.props.value}
                precision={0.5}
                readOnly={true}
                emptyIcon={<StarIcon style={{opacity: 0.55}} fontSize="inherit"/>}
              />

                <Box sx={{ml: 2}}>{labels[this.props.value]}</Box>
          </Box>
        );
    }
}
export default HoverRating;