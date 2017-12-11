import React, { Component } from 'react'
import ContentItem from './ContentItem'
import {Table} from 'reactstrap';

export default class Content extends Component {
render(){
  const{files,selectedName,user,tree,sideBarOption} = this.props;
  console.log("files in content:",files);
  return(
    <div>
    {
      files?
      <div>
      {
        files.files.map((contentItem)=>{
          return(
            <div>
            {
              <ContentItem name={contentItem} parentpath={files} files={tree[contentItem]} user={user} sideBarOption={sideBarOption}/>
            }
            </div>
            );
        }
      )
      }
      </div>:''
    }
    </div>    
  	);	
}
}