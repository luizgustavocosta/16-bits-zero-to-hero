import * as React from 'react';
import Avatar from '@mui/material/Avatar';
import Stack from '@mui/material/Stack';
import {deepOrange} from '@mui/material/colors';
import {Component} from "react";

class LetterAvatars extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    return (
      <Stack direction="row" spacing={2}>
        <Avatar sx={{bgcolor: deepOrange[500]}}>{this.props.name}</Avatar>
      </Stack>
    );
  }
}
export default LetterAvatars;