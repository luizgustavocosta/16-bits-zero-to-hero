import * as React from 'react';
import {Component} from 'react';
import Box from '@mui/material/Box';
import Avatar from '@mui/material/Avatar';
import Menu from '@mui/material/Menu';
import MenuItem from '@mui/material/MenuItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import Divider from '@mui/material/Divider';
import IconButton from '@mui/material/IconButton';
import Tooltip from '@mui/material/Tooltip';
import PersonAdd from '@mui/icons-material/PersonAdd';
import Settings from '@mui/icons-material/Settings';
import Logout from '@mui/icons-material/Logout';

class MovieMenu extends Component {
  constructor(props) {
    super(props);
    this.state = {
      anchorEL: null,
      open: false
    }
    this.handleClick = this.handleClick.bind(this)
    this.handleClose = this.handleClose.bind(this)
  }

  handleClick = (event) => {
    this.state.anchorEL = event.currentTarget;
    console.log("this.state.open["+this.state.open+"]")
    console.log("this.state.open["+this.state.anchorEL+"]")
    this.state.open = true;
  };
  handleClose = () => {
    this.state.anchorEL = null;
  };

  render() {
    return (
      <React.Fragment>
        <Box sx={{display: 'flex', alignItems: 'center', textAlign: 'center'}}>
          <Tooltip title="Account settings">
            <IconButton
              onClick={this.handleClick}
              size="small"
              sx={{ml: 2}}
              aria-controls={this.state.open ? 'account-menu' : undefined}
              aria-haspopup="true"
              aria-expanded={this.state.open ? 'true' : undefined}
            >
              <Avatar sx={{width: 32, height: 32}}>M</Avatar>
            </IconButton>
          </Tooltip>
        </Box>
        <Menu
          anchorEl={this.state.anchorEL}
          id="account-menu"
          open={true}
          onClose={this.handleClose}
          onClick={this.handleClose}
          PaperProps={{
            elevation: 0,
            sx: {
              overflow: 'visible',
              filter: 'drop-shadow(0px 2px 8px rgba(0,0,0,0.32))',
              mt: 1.5,
              '& .MuiAvatar-root': {
                width: 32,
                height: 32,
                ml: -0.5,
                mr: 1,
              },
              '&:before': {
                content: '""',
                display: 'block',
                position: 'absolute',
                top: 0,
                right: 14,
                width: 10,
                height: 10,
                bgcolor: 'background.paper',
                transform: 'translateY(-50%) rotate(45deg)',
                zIndex: 0,
              },
            },
          }}
          transformOrigin={{horizontal: 'right', vertical: 'top'}}
          anchorOrigin={{horizontal: 'right', vertical: 'bottom'}}
        >
          <MenuItem>
            <Avatar/> Profile
          </MenuItem>
          <MenuItem>
            <Avatar/> My account
          </MenuItem>
          <Divider/>
          <MenuItem>
            <ListItemIcon>
              <PersonAdd fontSize="small"/>
            </ListItemIcon>
            Add another account
          </MenuItem>
          <MenuItem>
            <ListItemIcon>
              <Settings fontSize="small"/>
            </ListItemIcon>
            Settings
          </MenuItem>
          <MenuItem>
            <ListItemIcon>
              <Logout fontSize="small"/>
            </ListItemIcon>
            Logout
          </MenuItem>
        </Menu>
      </React.Fragment>
    );
  }
}

export default MovieMenu;