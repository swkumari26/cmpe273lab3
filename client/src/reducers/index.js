import {SIGNUP_USER_SUCCESS,USER_CONTENT_SUCCESS,QUERY_SUCCESS,LOGIN_USER_REQUEST, LOGIN_USER_SUCCESS, LOGIN_USER_FAILURE, LOGOUT_USER,UPLOAD_SUCCESS,CONTENT_SELECTED} from '../actions/index';
import jwtDecode from 'jwt-decode';
import { combineReducers } from 'redux'
import { reducer as formReducer } from 'redux-form'
const initialState = {
    token: null,
    isAuthenticated: false,
    statusText: null,
    user:[],
    result: [],
    tree:null,
    contentSelected:{name:null,path:null},
    accounts:[{id:null,firstname:null,lastname:null,email:null},{id:null,firstname:null,lastname:null,email:null}],
    log:[],
    star:[]
};

const login = (state = initialState, action) => {

    switch (action.type) {         
        case LOGIN_USER_REQUEST:
        return{
            ...state,
            'statusText': null
        };
        case LOGIN_USER_SUCCESS:        
        return{
            ...state,
            'isAuthenticated': true,
            'statusText': 'You have been successfully logged in.',
            'user':action.user
        };
        case USER_CONTENT_SUCCESS:        
        return{
            ...state,
            // // 'token': [action.token],
            // 'isAuthenticated': true,
            // 'isAuthenticating': false,
            // 'statusText': 'You have been successfully logged in.',
            // 'user':action.user
            // 'result':[action.result][0],
            'tree':action.tree,
            'log':action.log,
            'star':action.star
        };        
        case SIGNUP_USER_SUCCESS:        
        return{
            ...state,
            'token': [action.token],
            'isAuthenticated': true,
            'isAuthenticating': false,
            'statusText': 'You have been successfully logged in.',
            'user':[action.user][0]
        };        
        case LOGIN_USER_FAILURE:
        return{
            ...state,
            'isAuthenticating': false,
            'isAuthenticated': false,
            'token': null,
            'statusText': `Authentication Error: ${action.status} ${action.statusText}`,
            'result':[],
            'tree':null
        };     
        case LOGOUT_USER:
        return{
            ...state,
            'isAuthenticated': false,
            'token': null,
            'statusText': 'You have been successfully logged out,please log in to continue..',
            'result':[],
            'tree':null
        };     
        case UPLOAD_SUCCESS:        
        return{
            ...state,
            'result': [action.result],
            'tree':[action.tree][0],
            'log':[action.log][0],
            'star':[action.star][0]
        }; 
        case QUERY_SUCCESS:        
        return{
            ...state,
            'accounts':action.accounts
        };                            
        default :
            return state;            
    }
};

const reducers = {
  login,
  form: formReducer     // <---- Mounted at 'form'
}
const reducer = combineReducers(reducers)
export default reducer;
