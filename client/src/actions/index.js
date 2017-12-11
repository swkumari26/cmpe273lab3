import { checkHttpStatus, parseJSON } from '../utils';
import bcrypt from 'bcryptjs';
import genSalt from '../utils/salt'
import history from '../history';

export const api = process.env.REACT_APP_DROPBOX || 'http://localhost:8080';
export const LOGIN_USER_SUCCESS='LOGIN_USER_SUCCESS';
export const SIGNUP_USER_SUCCESS='SIGNUP_USER_SUCCESS';
export const LOGIN_USER_FAILURE='LOGIN_USER_FAILURE';
export const LOGIN_USER_REQUEST='LOGIN_USER_REQUEST';
export const LOGOUT_USER='LOGOUT_USER';
export const FETCH_PROTECTED_DATA_REQUEST='FETCH_PROTECTED_DATA_REQUEST';
export const RECEIVE_PROTECTED_DATA='RECEIVE_PROTECTED_DATA';
export const UPLOAD_SUCCESS='UPLOAD_SUCCESS';
export const UPLOAD_FAILURE='UPLOAD_FAILURE';
export const UPLOAD_REQUEST='UPLOAD_REQUEST';
export const ADD_FOLDER_SPACE='ADD_FOLDER_SPACE';
export const CONTENT_SELECTED='CONTENT_SELECTED';
export const QUERY_SUCCESS='QUERY_SUCCESS';
export const USER_CONTENT_SUCCESS='USER_CONTENT_SUCCESS';

export function loginUserSuccess(data) {
  return {
    type: LOGIN_USER_SUCCESS,
    user:data[0]
  }
}
export function userContentSuccess(data,shared) {
  var tree = buildtree(data,shared),log=[],star=[]; 
  var log = data.sort((a,b)=>a.createdOn<b.createdOn);
  var star = data.filter((content)=>content.star);
  console.log(log);
  console.log("star is",star);
  return {
    type: USER_CONTENT_SUCCESS,
    tree,
    log,
    star
  }
}
export function signUpSuccess(data) {
  localStorage.setItem('token', data.response.token);
  return {
    type: SIGNUP_USER_SUCCESS,
    token:data.response.token,
    user:data.response.user
  }
}

export function loginUserFailure(error) {
  localStorage.removeItem('token');
  return {
    type: LOGIN_USER_FAILURE,
    status: error.response.status,
    statusText: error.response.statusText
  }
}

export function loginUserRequest() {
  return {
    type: LOGIN_USER_REQUEST
  }
}

export function uploadSuccess(data) {
var tree = buildtree(data.response.result),log=[],star=[]; 
console.log("tree in action",tree);     
  return {
    type: UPLOAD_SUCCESS,
    result:data.response.result,
    tree:tree,
    log,
    star
  }
}

export function querySuccess(data) {
  return {
    type: QUERY_SUCCESS,
    accounts:data,
  }
}

export function uploadFailure(error) {
  return {
    type: UPLOAD_FAILURE
    // status: error.response.status,
    // statusText: error.response.statusText
  }
}

export function uploadRequest() {
  return {
    type: UPLOAD_REQUEST
  }
}


export function logOut() {
    localStorage.removeItem('token');
    return {
        type: LOGOUT_USER
    }
}

export function logoutAndRedirect() {
    return dispatch =>{ 
        dispatch(logOut());
        history.push('/');
    }
}
export function contentSelected(path,name){
    return {
        type: CONTENT_SELECTED,
        path:path,
        name:name
    }
}
export function fetchUserContent(id){

    return dispatch =>{ 
        dispatch(loginUserRequest());        
        return fetch(`${api}/content/${id}`, {
            method: 'get',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
            })
            .then(checkHttpStatus)
            .then(parseJSON)
            .then(response => {
                try {
                    dispatch(userContentSuccess(response));  
                    // history.push('/log');                 
                } catch (e) {
                    dispatch(loginUserFailure({
                        response: {
                            status: 403,
                            statusText: 'Invalid token'
                        }
                    }));
                }
            })
            .catch(error => {
                dispatch(loginUserFailure({
                        response: {
                            status: 403,
                            statusText: 'Invalid token'
                        }
                    }));
            })   
        } 
}
export function fetchSharedContent(id){

    return dispatch =>{ 
        dispatch(loginUserRequest());        
        return fetch(`${api}/sharedContent/${id}`, {
            method: 'get'
            })
            .then(checkHttpStatus)
            .then(parseJSON)
            .then(response => {
                try {
                    dispatch(userContentSuccess(response,true));                  
                } catch (e) {
                    dispatch(loginUserFailure({
                        response: {
                            status: 403,
                            statusText: 'Invalid token'
                        }
                    }));
                }
            })
            .catch(error => {
                dispatch(loginUserFailure({
                        response: {
                            status: 403,
                            statusText: 'Invalid token'
                        }
                    }));
            })   
        } 
}
export function fetchGroupContent(id){

    return dispatch =>{ 
        dispatch(loginUserRequest());        
        return fetch(`${api}/group/${id}`, {
            method: 'get'
            })
            .then(checkHttpStatus)
            .then(parseJSON)
            .then(response => {
                try {
                    dispatch(userContentSuccess(response,true));                  
                } catch (e) {
                    dispatch(loginUserFailure({
                        response: {
                            status: 403,
                            statusText: 'Invalid token'
                        }
                    }));
                }
            })
            .catch(error => {
                dispatch(loginUserFailure({
                        response: {
                            status: 403,
                            statusText: 'Invalid token'
                        }
                    }));
            })   
        } 
}
export function loginRefresh(token){

    return dispatch =>{ 
        dispatch(loginUserRequest());        
        return fetch(`${api}/user/loginRefresh`, {
            method: 'get',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': token
            }
            })
            .then(checkHttpStatus)
            .then(parseJSON)
            .then(response => {
                try {
                    dispatch(loginUserSuccess(response));  
                    history.push('/log');                 
                } catch (e) {
                    dispatch(loginUserFailure({
                        response: {
                            status: 403,
                            statusText: 'Invalid token'
                        }
                    }));
                }
            })
            .catch(error => {
                dispatch(loginUserFailure({
                        response: {
                            status: 403,
                            statusText: 'Invalid token'
                        }
                    }));
            })   
        } 
}
export const loginUser = (user,signIn) => {
	return dispatch =>{	
        dispatch(loginUserRequest());
        if (signIn){        
        return fetch(`${api}/user/login`, {
            method: 'post',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
                body: JSON.stringify({email: user.email, password: user.password})
            })
            .then(checkHttpStatus)
            .then(parseJSON)
            .then(response => {
                try {
                    dispatch(loginUserSuccess(response));
                    history.push('/home');                    
                } catch (e) {
                    dispatch(loginUserFailure({
                        response: {
                            status: 403,
                            statusText: 'Invalid token'
                        }
                    }));
                }
            })
            .catch(error => {
                    dispatch(loginUserFailure({
                        response: {
                            status: 403,
                            statusText: 'Invalid token'
                        }
                    }));
            })
		}
		else
		{
        return fetch(`${api}/user/signUp`, {
            method: 'post',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
                body: JSON.stringify({firstname:user.firstname,lastname:user.lastname,email: user.email, password: user.password})
            })
            .then(checkHttpStatus)
            .then(parseJSON)
            .then(response => {
                try {
                    dispatch(loginUserSuccess({response:{
                                token:response.token,
                                result:response.result,
                                user:response.user,
                                contentMetaData:response.contentMetaData
                    }}));
                    history.push('/home');
                } catch (e) {
                    dispatch(loginUserFailure({
                        response: {
                            status: 403,
                            statusText: 'Invalid token'
                        }
                    }));
                }
            })
            .catch(error => {
                    dispatch(loginUserFailure({
                        response: {
                            status: 403,
                            statusText: 'Invalid token'
                        }
                    }));
            })			
		}
	}
}
export const itemClicked = (name) => {
    console.log("name received",name);
    history.push(name);  
}
export const uploadFile = (file) =>{
  return dispatch => {
    dispatch(uploadRequest());
       return fetch(`${api}/content/upload`, {
            method: 'POST',                       
            body: file
            })
            .then(checkHttpStatus) 
            .then(parseJSON)      
            .then(response => {
                try {
                    dispatch(userContentSuccess(response));      
                } catch (e) {
                    dispatch(uploadFailure({
                        response: {
                            status: 403,
                            statusText: 'Folder creation failed'
                        }
                    }));
                }
            })
            .catch(error => {
                    dispatch(uploadFailure({
                        response: {
                            status: 403,
                            statusText: 'Folder creation failed'
                        }
                    }));
            })
        }
  }

export const createFolder = (content) =>{
  return dispatch => {
    dispatch(uploadRequest());
       return fetch(`${api}/content/createFolder`, {
            method: 'post',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },               
            body: content
            })
            .then(checkHttpStatus) 
            .then(parseJSON)      
            .then(response => {
                try {
                    dispatch(userContentSuccess(response));      
                } catch (e) {
                    dispatch(uploadFailure({
                        response: {
                            status: 403,
                            statusText: 'Folder creation failed'
                        }
                    }));
                }
            })
            .catch(error => {
                    dispatch(uploadFailure({
                        response: {
                            status: 403,
                            statusText: 'Folder creation failed'
                        }
                    }));
            })
        }
  }

export const deleteContent = (contentId) =>{
  return dispatch => {
    dispatch(uploadRequest());
       return fetch(`${api}/content/${contentId}`, {
            method: 'delete'
            })
            .then(checkHttpStatus)
            .then(parseJSON)       
            .then(response => {
                try {
                    dispatch(userContentSuccess(response));             
                } catch (e) {
                    dispatch(uploadFailure({
                        response: {
                            status: 403,
                            statusText: 'File deletion failed'
                        }
                    }));
                }
            })
            .catch(error => {
                dispatch(uploadFailure(error));
            })
        }
  }  

export const starContent = (contentId) =>{
  return dispatch => {
    dispatch(uploadRequest());
       return fetch(`${api}/content/${contentId}`, {
            method: 'put'
            })
            .then(checkHttpStatus)
            .then(parseJSON)       
            .then(response => {
                try {
                    dispatch(userContentSuccess(response));             
                } catch (e) {
                    dispatch(uploadFailure({
                        response: {
                            status: 403,
                            statusText: 'File deletion failed'
                        }
                    }));
                }
            })
            .catch(error => {
                dispatch(uploadFailure(error));
            })
        }
  }  

export const shareContent = (email,accounts,contentId,sharedById) =>{
var id;
if(email.indexOf('@')>-1)
    {
    for(var i = 0; i < accounts.length; i++)
        {
        if(accounts[i].email == email)
            {
            id=accounts[i].id
            }
        }
    }
else
    {
    for(var i = 0; i < accounts.length; i++)
    {   var fullname = accounts[i].firstname+" "+accounts[i].lastname;
    if(fullname == email)
        {
            id=accounts[i].id
        }
    }
    }
  return dispatch => {
    dispatch(uploadRequest());
       return fetch(`${api}/sharedContent/share`, {
            method: 'post',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },               
            body: JSON.stringify({"sharedToId":id,"sharedById":sharedById,"contentId":contentId})
            })
            .then(checkHttpStatus)
            .then(parseJSON)     
            .catch(error => {
                dispatch(uploadFailure(error));
            })
        }
  } 
export const getAccounts = () =>{
  return dispatch => {
    dispatch(uploadRequest());
       return fetch(`${api}/user/all`, {
            method: 'get',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }             
            })
            .then(checkHttpStatus)
            .then(parseJSON)       
            .then(response => {
                try {
                    dispatch(querySuccess(response));            
                } catch (e) {
                    dispatch(uploadFailure({
                        response: {
                            status: 403,
                            statusText: 'Account fetch failed'
                        }
                    }));
                }
            })
            .catch(error => {
                dispatch(uploadFailure(error));
            })
        }
  }   

function buildtree(result,shared)
{
var tree = {
    root: {
    absolute_path: '',
    files: [],
    star:false,
    createdOn:null,
    members:null,
    createdBy:null,
    contentPath:null,
    rootFolder:result.id,
    contentId:null
  }
};

function buildTree(parts,content) {
  var lastDir = 'root';
  var abs_path = '';
  var currContentName = parts[parts.length-1];

  parts.map((name) =>{
      if (tree[lastDir].files.indexOf(name)===-1)
      {
      tree[lastDir].files.push(name);    
    }
    // It's a directory
    if (name.indexOf('.') === -1) {

      lastDir = name;
      abs_path += lastDir + '/';
    }
      if (!tree[name]) {
        tree[name] = {
          absolute_path: abs_path,
          files: [],
          star:false,
          createdOn:null,
          members:null,
          createdBy:null,
          contentPath:null,
          contentId:null         
        };
      }
  });
  tree[currContentName].createdOn = content.createdOn;
  tree[currContentName].createdBy = content.createdBy;
  tree[currContentName].star = content.star;
  tree[currContentName].rootFolder = content.rootFolder;
  tree[currContentName].contentId = content.id;
}
if(result){
result.map((content)=> {
if(shared){
    buildTree(content.contentName.split('/'),content);
}  
else if(content.contentPath){
    content.contentPath = content.contentPath.substring(1);
    buildTree(content.contentPath.split('/'),content);
    }
});
}
console.log("tree is:",tree);
return tree;    
}