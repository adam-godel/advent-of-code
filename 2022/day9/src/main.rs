use std::{collections::HashSet, fs};

fn part2(data: &str) -> usize {
    let mut prev = HashSet::from([(0, 0)]);
    let mut curs = vec![(0, 0); 10];
    for i in data.lines() {
        let mut iter = i.split_whitespace();
        let dir = iter.next().unwrap();
        let dist: u32 = iter.next().unwrap().parse().unwrap();
        for _ in 0..dist {
            curs[0] = match dir {
                "L" => (curs[0].0 - 1, curs[0].1),
                "R" => (curs[0].0 + 1, curs[0].1),
                "U" => (curs[0].0, curs[0].1 + 1),
                "D" => (curs[0].0, curs[0].1 - 1),
                _ => curs[0],
            };
            for i in 0..curs.len() - 1 {
                curs[i + 1] = if curs[i + 1].0 - curs[i].0 >= 2 {
                    (
                        curs[i + 1].0 - 1,
                        if curs[i + 1].1 - curs[i].1 > 0 {
                            curs[i + 1].1 - 1
                        } else if curs[i + 1].1 - curs[i].1 < 0 {
                            curs[i + 1].1 + 1
                        } else {
                            curs[i + 1].1
                        },
                    )
                } else if curs[i].0 - curs[i + 1].0 >= 2 {
                    (
                        curs[i + 1].0 + 1,
                        if curs[i + 1].1 - curs[i].1 > 0 {
                            curs[i + 1].1 - 1
                        } else if curs[i + 1].1 - curs[i].1 < 0 {
                            curs[i + 1].1 + 1
                        } else {
                            curs[i + 1].1
                        },
                    )
                } else if curs[i + 1].1 - curs[i].1 >= 2 {
                    (
                        if curs[i + 1].0 - curs[i].0 > 0 {
                            curs[i + 1].0 - 1
                        } else if curs[i + 1].0 - curs[i].0 < 0 {
                            curs[i + 1].0 + 1
                        } else {
                            curs[i + 1].0
                        },
                        curs[i + 1].1 - 1,
                    )
                } else if curs[i].1 - curs[i + 1].1 >= 2 {
                    (
                        if curs[i + 1].0 - curs[i].0 > 0 {
                            curs[i + 1].0 - 1
                        } else if curs[i + 1].0 - curs[i].0 < 0 {
                            curs[i + 1].0 + 1
                        } else {
                            curs[i + 1].0
                        },
                        curs[i + 1].1 + 1,
                    )
                } else {
                    curs[i + 1]
                };
            }
            prev.insert(curs[curs.len() - 1]);
        }
    }
    prev.len()
}

fn part1(data: &str) -> usize {
    let mut prev = HashSet::from([(0, 0)]);
    let mut curh = (0, 0);
    let mut curt = (0, 0);
    for i in data.lines() {
        let mut iter = i.split_whitespace();
        let dir = iter.next().unwrap();
        let dist: u32 = iter.next().unwrap().parse().unwrap();
        for _ in 0..dist {
            match dir {
                "L" => {
                    curh = (curh.0 - 1, curh.1);
                    if i32::abs(curt.0 - curh.0) >= 2 {
                        curt = (curh.0 + 1, curh.1);
                        prev.insert(curt);
                    }
                }
                "R" => {
                    curh = (curh.0 + 1, curh.1);
                    if i32::abs(curt.0 - curh.0) >= 2 {
                        curt = (curh.0 - 1, curh.1);
                        prev.insert(curt);
                    }
                }
                "U" => {
                    curh = (curh.0, curh.1 + 1);
                    if i32::abs(curt.1 - curh.1) >= 2 {
                        curt = (curh.0, curh.1 - 1);
                        prev.insert(curt);
                    }
                }
                _ => {
                    curh = (curh.0, curh.1 - 1);
                    if i32::abs(curt.1 - curh.1) >= 2 {
                        curt = (curh.0, curh.1 + 1);
                        prev.insert(curt);
                    }
                }
            }
        }
    }
    prev.len()
}

fn main() {
    let data = fs::read_to_string("day9.txt").unwrap();
    println!("{}\n{}", part1(&data), part2(&data));
}
