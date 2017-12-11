import React, { Component } from 'react'
import { connect } from 'react-redux'
import { Field, reduxForm, formValueSelector } from 'redux-form'
import { DropdownMenu, MenuItem } from 'react-bootstrap-dropdown-menu';
import NewFolder from 'react-icons/lib/md/folder-open';
import SharedFolder from 'react-icons/lib/md/folder-shared';
import EyeIcon from 'react-icons/lib/ti/eye-outline';
import ContentItem from './ContentItem'
import {uploadFile} from '../actions/index';
import {Link}  from 'react-router-dom'

export default class LogBody extends Component { 
render(){
  const{user,tree,log,star} = this.props; 
  return(
    <div className="container-fluid">
      <div className="col-lg-9">
        <div className="row">
          <h7><strong>Starred</strong></h7>
        </div>
        <div className="row">
        {
        (star.length===0)?
        (
        <div className="jumbotron-star">When you star items, they’ll show up here for easy access.</div>):
        (<div>
        {
          star.map((Content)=>{
        return(
          <div>
            {            
            <ContentItem name={Content.contentName} files={tree[Content.contentName]} user={user}/>
            }
          </div>
          )
        })
        }</div>)
        }   
        </div>       
        <div className="row">
          <h7><strong>Recent</strong></h7>
        </div>     
        <br/><br/>
        <div className="row">
        {
        (log.length===0)?
        (
        <div className="jumbotron-star">Recent created files and folders appear here!</div>):
        (<div>
        {
          log.map((Content)=>{
        return(
          <div>
            {            
            <ContentItem name={Content.contentName} files={tree[Content.contentName]} user={user}/>
            }
          </div>
          )
        })
        }</div>)
        }   
        </div>    
      </div>
      <div className="col-lg-3">
        <div className="float:right">
        </div>
      </div>
    </div>
    )
  }
}