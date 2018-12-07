import React, { Component } from 'react';
import { View, Text, FlatList, StyleSheet, Button } from 'react-native';
import { connect } from 'react-redux';
import { withNavigation } from 'react-navigation';
import { getGames } from './../actions/reducer';
import DayScreen from '../screens/DayScreen';

class GetGames extends Component {



  componentDidMount() {
    this.props.getGames();
  }
  renderItem = ({ item }) => (
    <View style={styles.item}>
      <Button
        onPress={() => this.props.navigation.navigate('Day', {game: item.game_id})}
        title={item.title}
        color="#841584"
        accessibilityLabel={"View details about game" + item.title}
      />
    </View>
  );
  render() {
    const { games } = this.props;
    return (
      <FlatList
        styles={styles.container}
        data={games}
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
  }
});

const mapStateToProps = state => {
  let storedGames = state.games.map(game => ({ key: game.game_id, ...game }));
  return {
    games: storedGames
  };
};

const mapDispatchToProps = {
  getGames
};

export default withNavigation(connect(mapStateToProps, mapDispatchToProps)(GetGames));
