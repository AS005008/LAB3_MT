import React, {Component} from 'react';
import { Dimensions, FlatList, StyleSheet, Text, TouchableOpacity, View, Image } from 'react-native';

const WIDTH = Dimensions.get("window").width;
const cards = [
  1,2,3,4,
  5,6,7,8,
  1,2,3,4,
  5,6,7,8,
  -1,
];
const opacities = [
  0,0,0,0,
  0,0,0,0,
  0,0,0,0,
  0,0,0,0,
  1,
];
const images = [
  require('./icons/1.ico'),
  require('./icons/2.ico'),
  require('./icons/3.ico'),
  require('./icons/4.ico'),
  require('./icons/5.ico'),
  require('./icons/6.ico'),
  require('./icons/7.ico'),
  require('./icons/8.ico'),
];

let newGame = 1;

export default class App extends Component {
  constructor(){
    super();
    this.state = {
      update: 0,
    };
  }

  _random = () => {
    if(newGame){
      for(let i = 0; i<cards.length - 1; i++){
        opacities[i] = 0;
      }
      for(let i = cards.length - 2; i > 0; i--){
        let j = Math.floor(Math.random() * (i+1));
        [cards[i], cards[j]] = [cards[j], cards[i]];
      }
      newGame = 0;
    }
  }

  _hide = (need, first, second) => {
    for(let i = 0; i<opacities.length; i++)
      if(opacities[i] != -1)
        opacities[i] = 0;
    if(need){
      opacities[first] = -1;
      opacities[second] = -1;
    }
    this.setState({update: this.state.update + 1});     
  }

  _onClick = (e, index) => {
    if(cards[index] == -1){
      newGame = 1;
      this._random();
      this.setState({update: 0});     
    } 
    else if(opacities[index] != -1){
      opacities[index] = 1;
      let first = -1;
      let second = -1;
      for(let i = 0; i<opacities.length - 1; i++){
        if(opacities[i] == 1){
          if(first == -1)
            first = i;
          else 
            second = i;
        }
      }
      if(second != -1){     
        if(cards[first] == cards[second]){
          setTimeout( () => {this._hide(true, first, second)}, 400);
        }
        else {
          setTimeout( () => {this._hide(false, first, second)}, 400);
        }
      }     
      this.setState({update: this.state.update + 1});    
    }
  }

  _renderItem = (item) => {
    let index = item.index;
    let _image = images[cards[index] - 1];
    let content = null;

    if( cards[index] == -1){
      content = <Text style={[{fontSize: 30, color: '#fff', opacity: 1, textTransform: 'uppercase'}]}>Reset</Text>;
    }
    else {
      content = <Image style={[styles.image, {opacity: opacities[index]}]} source={_image}/>;
    }

    return(
      <View style={[styles.card, {opacity: opacities[index] + 1}]}>
        <TouchableOpacity style={[styles.touchable]} onPress={ (e) => this._onClick(e, index)}>
          {content}
        </TouchableOpacity>
      </View>
    );
  }

  render(){
    this._random();
    return (
      <View style={styles.container}>
        <FlatList
          data = {cards}
          numColumns = {4}
          renderItem = {(index) => this._renderItem(index)}
          extraData = {this.state}
        />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    paddingTop: 40,
    backgroundColor: '#ffffff',
  },
  card: {
    backgroundColor: '#54c654',
    alignItems: 'center',
    height: WIDTH / 4 - 10,
    width: WIDTH / 4 - 10,
    justifyContent: 'center',
    flex: 1,
    margin: 5,
  },
  touchable: {
    height: WIDTH / 4 - 20,
    width: WIDTH / 4 - 20,
    justifyContent: 'center',
    alignItems: 'center',
  },
  image: {
    width: WIDTH / 8,
    height: WIDTH / 8,    
  }
});