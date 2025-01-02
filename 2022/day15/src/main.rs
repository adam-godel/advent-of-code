use range_union_find::{OverlapType, RangeUnionFind};
use std::{collections::HashMap, fs};

fn part2(data: &str) -> i64 {
    let mut ranges: HashMap<i32, RangeUnionFind<i32>> = HashMap::new();
    let max = 4000000;
    for target in 0..max {
        ranges.insert(target, RangeUnionFind::new());
        for line in data.lines() {
            let list: Vec<&str> = line.split_whitespace().collect();
            let sensor: (i32, i32) = (
                list[2][2..list[2].len() - 1].parse().unwrap(),
                list[3][2..list[3].len() - 1].parse().unwrap(),
            );
            let closest: (i32, i32) = (
                list[8][2..list[8].len() - 1].parse().unwrap(),
                list[9][2..].parse().unwrap(),
            );
            if (target - sensor.1).abs()
                <= (closest.0 - sensor.0).abs() + (closest.1 - sensor.1).abs()
            {
                ranges
                    .get_mut(&target)
                    .unwrap()
                    .insert_range(
                        &(sensor.0
                            - ((sensor.0 - closest.0).abs() + (sensor.1 - closest.1).abs()
                                - (target - sensor.1).abs())
                            ..=sensor.0
                                + ((sensor.0 - closest.0).abs() + (sensor.1 - closest.1).abs()
                                    - (target - sensor.1).abs())),
                    )
                    .unwrap();
            }
        }
        let mut full = RangeUnionFind::new();
        full.insert_range(&(0..=max)).unwrap();
        if ranges[&target].has_range(&(0..=max)) != Ok(OverlapType::Contained) {
            for i in 0..max {
                if !ranges[&target].has_element(&i) {
                    return (i as i64) * 4000000 + target as i64;
                }
            }
        }
    }
    -1
}

fn part1(data: &str) -> i32 {
    let mut ranges = RangeUnionFind::<i32>::new();
    let mut sub: Vec<i32> = Vec::new();
    for line in data.lines() {
        let list: Vec<&str> = line.split_whitespace().collect();
        let sensor: (i32, i32) = (
            list[2][2..list[2].len() - 1].parse().unwrap(),
            list[3][2..list[3].len() - 1].parse().unwrap(),
        );
        let closest: (i32, i32) = (
            list[8][2..list[8].len() - 1].parse().unwrap(),
            list[9][2..].parse().unwrap(),
        );
        let target = 2000000;
        if closest.1 == target && !sub.contains(&closest.0) {
            sub.push(closest.0);
        }
        if (target - sensor.1).abs() <= (closest.0 - sensor.0).abs() + (closest.1 - sensor.1).abs()
        {
            ranges
                .insert_range(
                    &(sensor.0
                        - ((sensor.0 - closest.0).abs() + (sensor.1 - closest.1).abs()
                            - (target - sensor.1).abs())
                        ..=sensor.0
                            + ((sensor.0 - closest.0).abs() + (sensor.1 - closest.1).abs()
                                - (target - sensor.1).abs())),
                )
                .unwrap();
        }
    }
    match ranges.has_range(&(i32::MIN..i32::MAX)) {
        Ok(OverlapType::Partial(a)) => a - (sub.len() as i32),
        _ => -1,
    }
}

fn main() {
    let data = fs::read_to_string("day15.txt").unwrap();
    println!("{}\n{}", part1(&data), part2(&data));
}
