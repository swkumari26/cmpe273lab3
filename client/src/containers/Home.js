import React, { Component } from 'react'
import { connect } from 'react-redux'
import history from '../history';
import LoginComponent from '../components/Login1'
import Header from '../components/Header'
import {fetchUserContent} from '../actions/index';
import Body from '../components/Body'
import { bindActionCreators } from 'redux';
import {Link}  from 'react-router-dom'
class Home extends Component {
        componentWillMount () {
            this.checkAuth(this.props.isAuthenticated);
        }

        componentWillReceiveProps (nextProps) {
            this.checkAuth(nextProps.isAuthenticated);
        }
        componentDidMount(){
            this.props.fetchUserContent(this.props.user.id);
        }
        checkAuth (isAuthenticated) {
            if (!isAuthenticated) {
             history.push('/');
            }
        }  
  render() {
  const{tree,token,user,statusText} = this.props; 
var files={};
console.log("tree in home is:",tree)
  console.log("parameters at home",this.props.match.params.folder);
  if(tree)
  {
  if(this.props.match.params.folder)
  {
    files = tree[this.props.match.params.folder];
    console.log("files in home",files); 
  }   
  else
  {
    if(tree[user.id])
    files = tree[user.id];
    else
      files = tree.root;
    console.log("files in home",files); 
  }
}
let username;
if(user){username= user.lastName+','+user.firstName;}
  return(
    <div className="row-fluid">
    <div className="col-lg-2">
    <div className="navbar navbar-fixed-left">
  <ul className="nav navbar-nav pull-left">
    <li><a href="#home"><i className="fa fa-home"></i><span> <img src="https://cfl.dropboxstatic.com/static/images/favicon-vflk5FiAC.ico"></img> </span></a></li>
   <li><Link to="" onClick={(e) => {e.preventDefault(); history.push('/home'); }}><h4>Home </h4></Link></li>
   <li><Link to="" onClick={(e) => {e.preventDefault(); history.push('/shared'); }}><h4>Shared Files </h4></Link></li>
   <li><Link to="" onClick={(e) => {e.preventDefault(); history.push('/group'); }}><h4>Groups</h4></Link></li>
   <li><Link to="" onClick={(e) => {e.preventDefault(); history.push('/log'); }}><h4>Activity Log </h4></Link></li>
   <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
  </ul>
  </div>
  </div>
  <div className="col-lg-10">
          <div className="row justify-content-md-center">
          <h4>{statusText}</h4>
        </div> 
  <br/><br/>
  <Header pageName="Home" userName={username} token={token}/>
  <br/><br/>
    <Body files={files} tree={tree} sideBarOption="/home/" user={user}/>
  </div>
  </div>
      )
      }
  }
  function mapStateToProps(state) {
    return{
        user:state.login.user,
        token: state.login.token,
        isAuthenticated: state.login.isAuthenticated,
        tree:state.login.tree,
        statusText:state.login.statusText
    }
  }
  function mapDispatchToProps(dispatch) {
    return {
        ...bindActionCreators({fetchUserContent},dispatch)
    };
}
export default connect(mapStateToProps, mapDispatchToProps)(Home); 