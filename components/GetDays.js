import React, { Component } from 'react';
import { View, Text, FlatList, StyleSheet, Button } from 'react-native';
import { connect } from 'react-redux';

import { getGameDays } from './../actions/reducer_days';

class GetDays extends Component {

  componentDidMount() {
    this.props.getGameDays(this.props.game);
  }
  renderItem = ({ item }) => (
    <View style={styles.item}>
      {  <Button title={item.day_title} color="#841584" accessibilityLabel={"View details about game" + item.day_title}></Button>}
    </View>
  );
  render() {
    const { days } = this.props;
    return (
    //      <Text>{ this.props.game }</Text>
      <FlatList
        styles={styles.container}
        data={days}
        renderItem={this.renderItem}
      />
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1
  },
  item: {
    padding: 16,
    borderBottomWidth: 1,
    borderBottomColor: '#ccc'
  }
});

const mapStateToProps = state => {
  let storeDays = state.days.map(day => ({ key: day.day_id, ...day }));
  return {
    days: storeDays
  };
};

const mapDispatchToProps = {
  getGameDays
};

export default connect(mapStateToProps, mapDispatchToProps)(GetDays);
