use std::{collections::HashMap, fs};

fn sim(pos: &Vec<(i32, i32)>, round: u32) -> Vec<(i32, i32)> {
    let mut next: HashMap<(i32, i32), (i32, i32)> = HashMap::new();
    for i in pos {
        let present = [
            pos.contains(&(i.0 - 1, i.1)),
            pos.contains(&(i.0 - 1, i.1 + 1)),
            pos.contains(&(i.0, i.1 + 1)),
            pos.contains(&(i.0 + 1, i.1 + 1)),
            pos.contains(&(i.0 + 1, i.1)),
            pos.contains(&(i.0 + 1, i.1 - 1)),
            pos.contains(&(i.0, i.1 - 1)),
            pos.contains(&(i.0 - 1, i.1 - 1)),
        ];
        let mut to_insert = (i.0, i.1);
        let empty = (false, false, false);
        if present.iter().filter(|j| **j).count() > 0 {
            for j in round..round + 4 {
                match j % 4 {
                    0 => {
                        if (present[7], present[0], present[1]) == empty {
                            to_insert = (i.0 - 1, i.1);
                            break;
                        }
                    }
                    1 => {
                        if (present[3], present[4], present[5]) == empty {
                            to_insert = (i.0 + 1, i.1);
                            break;
                        }
                    }
                    2 => {
                        if (present[5], present[6], present[7]) == empty {
                            to_insert = (i.0, i.1 - 1);
                            break;
                        }
                    }
                    3 => {
                        if (present[1], present[2], present[3]) == empty {
                            to_insert = (i.0, i.1 + 1);
                            break;
                        }
                    }
                    _ => (),
                }
            }
        }
        if let Some(j) = next.remove(&to_insert) {
            next.insert(*i, *i);
            next.insert(j, j);
        } else {
            next.insert(to_insert, *i);
        }
    }
    next.keys().map(|i| *i).collect()
}

fn part2(data: &str) -> u32 {
    let mut pos = Vec::new();
    for (idx, i) in data.lines().enumerate() {
        pos.append(
            &mut i
                .chars()
                .enumerate()
                .filter(|(_, i)| *i == '#')
                .map(|(jdx, _)| (idx as i32, jdx as i32))
                .collect(),
        );
    }
    let mut round = 0;
    let mut last_pos = vec![(-1, -1)];
    while !last_pos.iter().all(|i| pos.contains(i)) {
        last_pos = pos.clone();
        pos = sim(&pos, round);
        round += 1;
    }
    round
}

fn part1(data: &str) -> u32 {
    let mut pos = Vec::new();
    for (idx, i) in data.lines().enumerate() {
        pos.append(
            &mut i
                .chars()
                .enumerate()
                .filter(|(_, i)| *i == '#')
                .map(|(jdx, _)| (idx as i32, jdx as i32))
                .collect(),
        );
    }
    for round in 0..10 {
        pos = sim(&pos, round);
    }
    let (mini, maxi, minj, maxj) = (
        pos.iter().map(|i| i.0).min().unwrap(),
        pos.iter().map(|i| i.0).max().unwrap(),
        pos.iter().map(|i| i.1).min().unwrap(),
        pos.iter().map(|i| i.1).max().unwrap(),
    );
    ((maxj - minj + 1) * (maxi - mini + 1) - (pos.len() as i32)) as u32
}

fn main() {
    let data = fs::read_to_string("day23.txt").unwrap();
    println!("{}\n{}", part1(&data), part2(&data));
}
