import React from 'react';
import { 
  Text, 
  View, 
  TouchableHighlight,
  Modal,
  StyleSheet
 } from 'react-native';

export default class ModalView extends React.Component {
  constructor() {
    super();
    this.state = {
      modalVisible: false,
      item: { "name" : "none", "email" : "none", "body" : "none"},
    };
  }
  
  componentWillReceiveProps(nextProps) {
    this.setState({
      modalVisible: nextProps.modalVisible,
      item: nextProps.item,
    })
  }
  
  render() {
    return (
       <Modal
        style={styles.modalView}
        animationType="slide"
        transparent={ false }
        visible={ this.state.modalVisible }
       > 
         <View>
           <View>
            <Text style={styles.titleText}>{ this.state.item.name }</Text>
            <Text style={styles.bodyText}>{ this.state.item.email }</Text>
            <Text style={styles.bodyText}>{ this.state.item.body }</Text>
            <TouchableHighlight
              style={styles.hideButton}
              onPress={() => { this.setState({ modalVisible: false}) }}
            > 
              <Text style={styles.textButton}>Back</Text>
            </TouchableHighlight>
           </View>
         </View>
       </Modal>
    );
  }
}

const styles = StyleSheet.create({
  modalView: {
    margin: 20,
    backgroundColor: "#494949",
    borderRadius: 20,
    padding: 35,
    alignItems: "center",
  },
  hideButton: {
    backgroundColor: "#007600",
    padding: 10,
    elevation: 2,
    margin: 10,
  },
  textButton: {
    color : '#fff',
    textAlign: 'center',
    fontSize: 20,
  },
  titleText: {
    margin: 10,
    fontSize: 30,
    textTransform: 'uppercase',
    fontWeight: "bold",
  },
  bodyText: {
    margin: 10,
  }
  
});
