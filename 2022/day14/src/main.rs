use std::{cmp, collections::HashMap, fs};

fn part2(rocks: &HashMap<u32, Vec<u32>>) -> usize {
    let maxy = rocks.iter().max_by(|a, b| a.cmp(&b)).unwrap().0 + 1;
    let mut sand: Vec<(u32, u32)> = Vec::new();
    let mut cur = (500, 0);
    while !sand.contains(&(500, 0)) {
        cur = if cur.1 < maxy
            && !sand.contains(&(cur.0, cur.1 + 1))
            && (!rocks.contains_key(&(cur.1 + 1)) || !rocks[&(cur.1 + 1)].contains(&cur.0))
        {
            (cur.0, cur.1 + 1)
        } else if cur.1 < maxy
            && !sand.contains(&(cur.0 - 1, cur.1 + 1))
            && (!rocks.contains_key(&(cur.1 + 1)) || !rocks[&(cur.1 + 1)].contains(&(cur.0 - 1)))
        {
            (cur.0 - 1, cur.1 + 1)
        } else if cur.1 < maxy
            && !sand.contains(&(cur.0 + 1, cur.1 + 1))
            && (!rocks.contains_key(&(cur.1 + 1)) || !rocks[&(cur.1 + 1)].contains(&(cur.0 + 1)))
        {
            (cur.0 + 1, cur.1 + 1)
        } else {
            sand.push(cur);
            (500, 0)
        };
    }
    sand.len()
}

fn part1(rocks: &HashMap<u32, Vec<u32>>) -> usize {
    let maxy = rocks.iter().max_by(|a, b| a.cmp(&b)).unwrap();
    let mut sand: Vec<(u32, u32)> = Vec::new();
    let mut cur = (500, 0);
    while cur.1 <= *maxy.0 {
        cur = if (!rocks.contains_key(&(cur.1 + 1)) || !rocks[&(cur.1 + 1)].contains(&cur.0))
            && !sand.contains(&(cur.0, cur.1 + 1))
        {
            (cur.0, cur.1 + 1)
        } else if (!rocks.contains_key(&(cur.1 + 1)) || !rocks[&(cur.1 + 1)].contains(&(cur.0 - 1)))
            && !sand.contains(&(cur.0 - 1, cur.1 + 1))
        {
            (cur.0 - 1, cur.1 + 1)
        } else if (!rocks.contains_key(&(cur.1 + 1)) || !rocks[&(cur.1 + 1)].contains(&(cur.0 + 1)))
            && !sand.contains(&(cur.0 + 1, cur.1 + 1))
        {
            (cur.0 + 1, cur.1 + 1)
        } else {
            sand.push(cur);
            (500, 0)
        };
    }
    sand.len()
}

fn main() {
    let data = fs::read_to_string("day14.txt").unwrap();
    let mut rocks: HashMap<u32, Vec<u32>> = HashMap::new();
    for line in data.lines() {
        let borders: Vec<(u32, u32)> = line
            .split(" -> ")
            .map(|j| {
                let mut pt = j.split(",");
                (
                    pt.next().unwrap().parse().unwrap(),
                    pt.next().unwrap().parse().unwrap(),
                )
            })
            .collect();
        for i in 0..borders.len() - 1 {
            if borders[i].0 == borders[i + 1].0 {
                for j in cmp::min(borders[i].1, borders[i + 1].1)
                    ..cmp::max(borders[i].1, borders[i + 1].1) + 1
                {
                    if !rocks.contains_key(&j) {
                        rocks.insert(j, Vec::new());
                    }
                    rocks.get_mut(&j).unwrap().push(borders[i].0);
                }
            } else if borders[i].1 == borders[i + 1].1 {
                for j in cmp::min(borders[i].0, borders[i + 1].0)
                    ..cmp::max(borders[i].0, borders[i + 1].0) + 1
                {
                    if !rocks.contains_key(&borders[i].1) {
                        rocks.insert(borders[i].1, Vec::new());
                    }
                    rocks.get_mut(&borders[i].1).unwrap().push(j);
                }
            }
        }
    }
    println!("{}\n{}", part1(&rocks), part2(&rocks));
}
