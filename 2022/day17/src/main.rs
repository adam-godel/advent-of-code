use std::fs;

#[derive(PartialEq, Clone)]
struct Rock {
    wind: u32,
    piece: u32,
    rel_max: Vec<i32>,
    height_change: u32,
}

fn part2(data: &str) -> u64 {
    let mut maxy = 0;
    let mut maxys: Vec<i32> = vec![0; 7];
    let mut wind_idx = 0;
    let mut height_changes: Vec<Rock> = Vec::new();
    let mut rocks: Vec<(u32, u32)> = Vec::new();
    let mut cur = (3, 4);
    let mut iter = data.chars();
    let init = 2500;
    for num in 0..init {
        let mut valid = true;
        while valid {
            let push = iter.next().unwrap_or_else(|| {
                iter = data.chars();
                iter.next().unwrap()
            });
            wind_idx = (wind_idx + 1) % (data.len() as u32);
            let new_pos = (if push == '>' { cur.0 + 1 } else { cur.0 - 1 }, cur.1);
            valid = match num % 5 {
                0 => {
                    new_pos.0 >= 1
                        && new_pos.0 <= 4
                        && ((push == '>' && !rocks.contains(&(new_pos.0 + 3, new_pos.1)))
                            || (push == '<' && !rocks.contains(&new_pos)))
                }
                1 => {
                    new_pos.0 >= 2
                        && new_pos.0 <= 6
                        && !rocks.contains(&new_pos)
                        && ((push == '>' && !rocks.contains(&(new_pos.0 + 1, new_pos.1 + 1)))
                            || (push == '<' && !rocks.contains(&(new_pos.0 - 1, new_pos.1 + 1))))
                }
                2 => {
                    new_pos.0 >= 1
                        && new_pos.0 <= 5
                        && ((push == '>'
                            && !rocks.contains(&(new_pos.0 + 2, new_pos.1))
                            && !rocks.contains(&(new_pos.0 + 2, new_pos.1 + 1))
                            && !rocks.contains(&(new_pos.0 + 2, new_pos.1 + 2)))
                            || (push == '<' && !rocks.contains(&new_pos)))
                }
                3 => {
                    new_pos.0 >= 1
                        && new_pos.0 <= 7
                        && !rocks.contains(&new_pos)
                        && !rocks.contains(&(new_pos.0, new_pos.1 + 1))
                        && !rocks.contains(&(new_pos.0, new_pos.1 + 2))
                        && !rocks.contains(&(new_pos.0, new_pos.1 + 3))
                }
                4 => {
                    new_pos.0 >= 1
                        && new_pos.0 <= 6
                        && ((push == '>'
                            && !rocks.contains(&(new_pos.0 + 1, new_pos.1))
                            && !rocks.contains(&(new_pos.0 + 1, new_pos.1 + 1)))
                            || (push == '<'
                                && !rocks.contains(&new_pos)
                                && !rocks.contains(&(new_pos.0, new_pos.1 + 1))))
                }
                _ => false,
            };
            if valid {
                cur = new_pos;
            }
            let new_pos = (cur.0, cur.1 - 1);
            valid = new_pos.1 > 0
                && match num % 5 {
                    0 => {
                        !rocks.contains(&new_pos)
                            && !rocks.contains(&(new_pos.0 + 1, new_pos.1))
                            && !rocks.contains(&(new_pos.0 + 2, new_pos.1))
                            && !rocks.contains(&(new_pos.0 + 3, new_pos.1))
                    }
                    1 => {
                        !rocks.contains(&new_pos)
                            && !rocks.contains(&(new_pos.0 - 1, new_pos.1 + 1))
                            && !rocks.contains(&(new_pos.0 + 1, new_pos.1 + 1))
                    }
                    2 => {
                        !rocks.contains(&new_pos)
                            && !rocks.contains(&(new_pos.0 + 1, new_pos.1))
                            && !rocks.contains(&(new_pos.0 + 2, new_pos.1))
                    }
                    3 => !rocks.contains(&new_pos),
                    4 => !rocks.contains(&new_pos) && !rocks.contains(&(new_pos.0 + 1, new_pos.1)),
                    _ => false,
                };
            if valid {
                cur = new_pos;
            }
        }
        let mut to_push = match num % 5 {
            0 => vec![
                cur,
                (cur.0 + 1, cur.1),
                (cur.0 + 2, cur.1),
                (cur.0 + 3, cur.1),
            ],
            1 => vec![
                cur,
                (cur.0 - 1, cur.1 + 1),
                (cur.0, cur.1 + 1),
                (cur.0 + 1, cur.1 + 1),
                (cur.0, cur.1 + 2),
            ],
            2 => vec![
                cur,
                (cur.0 + 1, cur.1),
                (cur.0 + 2, cur.1),
                (cur.0 + 2, cur.1 + 1),
                (cur.0 + 2, cur.1 + 2),
            ],
            3 => vec![
                cur,
                (cur.0, cur.1 + 1),
                (cur.0, cur.1 + 2),
                (cur.0, cur.1 + 3),
            ],
            4 => vec![
                cur,
                (cur.0 + 1, cur.1),
                (cur.0, cur.1 + 1),
                (cur.0 + 1, cur.1 + 1),
            ],
            _ => Vec::new(),
        };
        let height = to_push.iter().map(|i| i.1).max().unwrap() as i32;
        for i in 1..maxys.len() + 1 {
            let to_draw: Vec<u32> = to_push
                .iter()
                .filter(|j| j.0 == (i as u32))
                .map(|j| j.1)
                .collect();
            if to_draw.len() == 0 {
                continue;
            }
            maxys[i - 1] = *to_draw.iter().max().unwrap() as i32;
        }
        rocks.append(&mut to_push);
        let mut height_change: u32 = 0;
        if height > maxy {
            height_change = (height - maxy) as u32;
            maxy = height;
        }
        let rel_max = maxys.iter().map(|i| i - maxy).collect();
        height_changes.push(Rock {
            wind: wind_idx,
            piece: num % 5,
            rel_max,
            height_change,
        });
        cur = (if num % 5 == 0 { 4 } else { 3 }, (maxy + 4) as u32);
    }
    let mut uniq = Vec::new();
    let mut cycle = (0, 0);
    for i in 0..height_changes.len() {
        if uniq.contains(&height_changes[i]) {
            cycle = (
                uniq.iter().position(|j| j == &height_changes[i]).unwrap(),
                i,
            );
            break;
        }
        uniq.push(height_changes[i].clone());
    }
    let mut result: u64 = 0;
    for i in cycle.0..cycle.1 {
        result += height_changes[i].height_change as u64;
    }
    result *= (1000000000000 - cycle.0 as u64) / (cycle.1 - cycle.0) as u64;
    for i in 0..cycle.0 {
        result += height_changes[i].height_change as u64;
    }
    for i in cycle.0..(cycle.0 + (1000000000000 - cycle.0) % (cycle.1 - cycle.0)) {
        result += height_changes[i].height_change as u64;
    }
    result
}

fn part1(data: &str) -> u32 {
    let mut maxy = 0;
    let mut rocks: Vec<(u32, u32)> = Vec::new();
    let mut cur = (3, 4);
    let mut iter = data.chars();
    for num in 0..2022 {
        let mut valid = true;
        while valid {
            let push = iter.next().unwrap_or_else(|| {
                iter = data.chars();
                iter.next().unwrap()
            });
            let new_pos = (if push == '>' { cur.0 + 1 } else { cur.0 - 1 }, cur.1);
            valid = match num % 5 {
                0 => {
                    new_pos.0 >= 1
                        && new_pos.0 <= 4
                        && ((push == '>' && !rocks.contains(&(new_pos.0 + 3, new_pos.1)))
                            || (push == '<' && !rocks.contains(&new_pos)))
                }
                1 => {
                    new_pos.0 >= 2
                        && new_pos.0 <= 6
                        && !rocks.contains(&new_pos)
                        && ((push == '>' && !rocks.contains(&(new_pos.0 + 1, new_pos.1 + 1)))
                            || (push == '<' && !rocks.contains(&(new_pos.0 - 1, new_pos.1 + 1))))
                }
                2 => {
                    new_pos.0 >= 1
                        && new_pos.0 <= 5
                        && ((push == '>'
                            && !rocks.contains(&(new_pos.0 + 2, new_pos.1))
                            && !rocks.contains(&(new_pos.0 + 2, new_pos.1 + 1))
                            && !rocks.contains(&(new_pos.0 + 2, new_pos.1 + 2)))
                            || (push == '<' && !rocks.contains(&new_pos)))
                }
                3 => {
                    new_pos.0 >= 1
                        && new_pos.0 <= 7
                        && !rocks.contains(&new_pos)
                        && !rocks.contains(&(new_pos.0, new_pos.1 + 1))
                        && !rocks.contains(&(new_pos.0, new_pos.1 + 2))
                        && !rocks.contains(&(new_pos.0, new_pos.1 + 3))
                }
                4 => {
                    new_pos.0 >= 1
                        && new_pos.0 <= 6
                        && ((push == '>'
                            && !rocks.contains(&(new_pos.0 + 1, new_pos.1))
                            && !rocks.contains(&(new_pos.0 + 1, new_pos.1 + 1)))
                            || (push == '<'
                                && !rocks.contains(&new_pos)
                                && !rocks.contains(&(new_pos.0, new_pos.1 + 1))))
                }
                _ => false,
            };
            if valid {
                cur = new_pos;
            }
            let new_pos = (cur.0, cur.1 - 1);
            valid = new_pos.1 > 0
                && match num % 5 {
                    0 => {
                        !rocks.contains(&new_pos)
                            && !rocks.contains(&(new_pos.0 + 1, new_pos.1))
                            && !rocks.contains(&(new_pos.0 + 2, new_pos.1))
                            && !rocks.contains(&(new_pos.0 + 3, new_pos.1))
                    }
                    1 => {
                        !rocks.contains(&new_pos)
                            && !rocks.contains(&(new_pos.0 - 1, new_pos.1 + 1))
                            && !rocks.contains(&(new_pos.0 + 1, new_pos.1 + 1))
                    }
                    2 => {
                        !rocks.contains(&new_pos)
                            && !rocks.contains(&(new_pos.0 + 1, new_pos.1))
                            && !rocks.contains(&(new_pos.0 + 2, new_pos.1))
                    }
                    3 => !rocks.contains(&new_pos),
                    4 => !rocks.contains(&new_pos) && !rocks.contains(&(new_pos.0 + 1, new_pos.1)),
                    _ => false,
                };
            if valid {
                cur = new_pos;
            }
        }
        let mut to_push = match num % 5 {
            0 => vec![
                cur,
                (cur.0 + 1, cur.1),
                (cur.0 + 2, cur.1),
                (cur.0 + 3, cur.1),
            ],
            1 => vec![
                cur,
                (cur.0 - 1, cur.1 + 1),
                (cur.0, cur.1 + 1),
                (cur.0 + 1, cur.1 + 1),
                (cur.0, cur.1 + 2),
            ],
            2 => vec![
                cur,
                (cur.0 + 1, cur.1),
                (cur.0 + 2, cur.1),
                (cur.0 + 2, cur.1 + 1),
                (cur.0 + 2, cur.1 + 2),
            ],
            3 => vec![
                cur,
                (cur.0, cur.1 + 1),
                (cur.0, cur.1 + 2),
                (cur.0, cur.1 + 3),
            ],
            4 => vec![
                cur,
                (cur.0 + 1, cur.1),
                (cur.0, cur.1 + 1),
                (cur.0 + 1, cur.1 + 1),
            ],
            _ => Vec::new(),
        };
        let height = to_push.iter().map(|i| i.1).max().unwrap();
        rocks.append(&mut to_push);
        if height > maxy {
            maxy = height;
        }
        cur = (if num % 5 == 0 { 4 } else { 3 }, maxy + 4);
    }
    maxy
}

fn main() {
    let data = fs::read_to_string("day17.txt").unwrap();
    println!("{}\n{}", part1(&data), part2(&data));
}
