import React, {Component} from 'react';
import { 
  FlatList, 
  Text, 
  View, 
  TouchableOpacity, 
 } from 'react-native';
import ModalView from './ModalView';

let url = "https://jsonplaceholder.typicode.com/comments";

export default class App extends Component {
  constructor(props){
    super(props);
    this.state = {
      isLoading: true,
      modalVisible: false,
      item: { "name" : "none", "email" : "none", "body" : "none"},
    };
  }

  componentDidMount(){
    return fetch(url)
    .then((responce) => responce.json())
    .then((responceJson) => {
      this.setState({
        isLoading: false,
        dataSource: responceJson,
        modalVisible: false,
      });
    })
  }

  clickItem = (item) => {
    this.setState({
      modalVisible: true,
      item: item,
    });
  }

  getItem = (item) => {
    return(
      <TouchableOpacity 
        style={[{padding: 20, margin: 5, backgroundColor: '#efddb3'}]}
        onPress={() => this.clickItem(item)}
      >
        <Text numberOfLines={2}>{item.body}</Text>
      </TouchableOpacity>
    )
  }

  render(){
    return(
      <>
      <View style={{paddingTop:25}}>
        <ModalView
          item = {this.state.item}
          modalVisible = {this.state.modalVisible}
          setModalVisible = { () => {this.setState({modalVisible:true}) }}
        />
        <FlatList
          data={this.state.dataSource}
          renderItem={({item}) => this.getItem(item)}
          keyExtractor={({id}) => id}
        />
      </View>
      </>
    );
  }
}