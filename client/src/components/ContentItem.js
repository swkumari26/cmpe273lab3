import React, { Component } from 'react';
import FolderIcon from 'react-icons/lib/md/folder';
import GroupIcon from 'react-icons/lib/fa/group';
import FileIcon from 'react-icons/lib/go/file-text';
import PdfIcon from 'react-icons/lib/fa/file-pdf-o';
import StarIcon from 'react-icons/lib/fa/star';
import {Link}  from 'react-router-dom'
import history from '../history';
import { bindActionCreators } from 'redux';
import {createFolder,deleteContent,starContent,shareContent,getAccounts} from '../actions/index';
import { connect } from 'react-redux'
import Modal from 'react-modal'
import {api} from '../actions/index'

class ContentItem extends Component { 

    constructor(props) {
        super(props)
        this.state = {
              modalIsOpen:false,
              content:{
                  rootFolder:null,
                  contentPath:null,
                  contentName:null,
                  createdBy:null,
                  createdOn:null,
                  star:null
                }
              }
        this.handleKeyPress = this.handleKeyPress.bind(this);
    }
   handleKeyPress = (event) => {
        if(event.key === "Enter")
        { 
          const content = this.state.content;
          content.contentName = event.target.value;
          if(this.props.parentpath.rootFolder)
            content.rootFolder=this.props.parentpath.rootFolder;
          else
            content.rootFolder=this.props.user.id;
          if(this.props.parentpath.contentPath)
            content.contentPath = this.props.parentpath.content_path+'/'+event.target.value;
          else
            content.contentPath = '/'+this.props.user.id+'/'+event.target.value;
          content.createdBy = this.props.user.id;
          content.createdOn = new Date();
          content.star = false;
          this.setState({content});
          this.props.createFolder(JSON.stringify(this.state.content));
        }
      }                
render(){  
  const{user,name,parentpath,files,sideBarOption,deleteContent,shareContent,starContent,accounts,getAccounts} = this.props;
  const{modalIsOpen} = this.state;
  let displayIcon,buttonOptions
  let link = sideBarOption+name;
  let pathWithName,createdOn;
  if(files)
  {
  pathWithName = files.contentPath ;
  createdOn = (new Date(files.createdOn)).toDateString();
  }
    if (name)
    {
  	if (name.indexOf('.') > -1)
  		{	

      if(files.star)
          {        
        if(name.indexOf('pdf') > -1)
        {
        displayIcon = (
          <Link to=""><PdfIcon size={50}/><span>{name}&nbsp;&nbsp;</span><StarIcon size={20}/></Link>
          )
        }
        else
        {
        displayIcon = (
          <Link to=""><FileIcon size={50}/><span>{name}&nbsp;&nbsp;</span><StarIcon size={20}/></Link>
          )  
        }
      }
      else
      {
        if(name.indexOf('pdf') > -1)
        {        
        displayIcon = (
          <Link to=""><PdfIcon size={50}/><span>{name}     </span></Link>
          )
        }
        else
        {
        displayIcon = (
          <Link to=""><FileIcon size={50}/><span>{name}     </span></Link>
          )  
        }
      }
        buttonOptions = (<div>
          <button className="btn btn-default btn-sm" onClick={(e) => {e.preventDefault();getAccounts();this.setState({modalIsOpen:true});}}>Share</button>
          {
          (files.createdBy==user.id)?
          (<button className="btn btn-default btn-sm" onClick={(e) => {e.preventDefault(); deleteContent(files.contentId); }}>Delete</button>):""
          }
          <a className="btn btn-default btn-sm" href={"http://localhost:8080/content/download/"+files.contentId} download>Download</a>
          <button className="btn btn-default btn-sm" onClick={(e) => {e.preventDefault(); starContent(files.contentId); }}>{files.star?"UnStar":"Star"}</button>
          </div>
          )               
    		}
    	else{
        if(files.star)
        {
  			displayIcon = (
        <Link to="" onClick={(e) => {e.preventDefault(); history.push(link); }}><FolderIcon size={50}/><span>{name}    </span><StarIcon size={20}/></Link>
        )
      }
      else{
        if(sideBarOption=='/group/')
        displayIcon = (
        <Link to="" onClick={(e) => {e.preventDefault(); history.push(link); }}><GroupIcon size={50}/><span>{name}    </span></Link>
        )
        else
        displayIcon = (
        <Link to="" onClick={(e) => {e.preventDefault(); history.push(link); }}><FolderIcon size={50}/><span>{name}    </span></Link>
        )        
      }
        buttonOptions = (<div>
          <button className="btn btn-default btn-sm" onClick={(e) => {e.preventDefault();getAccounts();this.setState({modalIsOpen:true});}}>Share</button>
          {
          (files.createdBy==user.id)?
          (<button className="btn btn-default btn-sm" onClick={(e) => {e.preventDefault(); deleteContent(files.contentId); }}>Delete</button>):""
          }          
          <button className="btn btn-default btn-sm" onClick={(e) => {e.preventDefault(); starContent(files.contentId); }}>{files.star?"UnStar":"Star"}</button>
          </div>
          )        
      }
    }
    else
    {
        displayIcon = (
        <a><FolderIcon size={50}/><input type="text" onKeyPress={this.handleKeyPress}/></a>
        )       
    }
  return(
  <div>
  <table className="table">
  <tr>
    <td className="col-lg-4 col-md-4 col-sm-4">
    {displayIcon}
  	</td>
    <td className="col-lg-4 col-md-4 col-sm-4">
    {createdOn}
    </td>   
    <td className="col-lg-4 col-md-4 col-sm-4">
    <div className="float:right">
      {buttonOptions}        
    </div>
    </td>
    </tr> 
    </table> 
    <Modal 
      isOpen={modalIsOpen} 
      contentLabel='Modal'
      style={{overlay:{},content:{bottom:"50%",left:"30%",right:"30%",border:"2px solid #ccc"}}}       
      >
    <div className="modal-text">
    {displayIcon}<button className="close" onClick={(e) => {e.preventDefault();this.setState({modalIsOpen:false});}}><span aria-hidden={true}>&times;</span></button>
    <hr/>
    To: <input id="userID" list="accounts" placeholder="Email or name" className="inputmodal" ref={(input) => this.input = input}></input>
    <datalist id="accounts">
    {
      accounts.map((account)=>{
        return (<div><option data-id={account.id} value={account.email}></option>
                <option data-value={account.id} value={account.firstname+' '+account.lastname}></option></div>)
      })
    }
    </datalist>
    <hr/>
    <Link to="">Create link to share</Link>
    <hr/>
    <button className="btn btn-primary" onClick={(e) => {e.preventDefault();shareContent(this.input.value,accounts,files.contentId,user.id);this.setState({modalIsOpen:false});}}>Share</button>
    </div>
    </Modal>
    </div>   
     );	
}
}

  function mapStateToProps(state) {
    return{
      accounts:state.login.accounts
    }
    }

function mapDispatchToProps(dispatch) {
    return {
        ...bindActionCreators({createFolder,deleteContent,starContent,shareContent,getAccounts},dispatch)
    };
}
export default connect(mapStateToProps,mapDispatchToProps)(ContentItem);