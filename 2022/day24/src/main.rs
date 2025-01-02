use std::fs;

struct Pos {
    i: i32,
    j: i32,
    step: usize,
    blizzards: Vec<(i32, i32, usize)>,
}

fn part2(data: &str) -> usize {
    let grid: Vec<Vec<char>> = data.lines().map(|i| i.chars().collect()).collect();
    let mut blizzards: Vec<(i32, i32, usize)> = Vec::new();
    for (idx, i) in data.lines().enumerate() {
        blizzards.append(
            &mut i
                .chars()
                .enumerate()
                .filter(|(_, i)| ">v<^".contains(*i))
                .map(|(jdx, j)| (idx as i32, jdx as i32, ">v<^".find(j).unwrap()))
                .collect(),
        );
    }
    let dirs = [(0, 1), (1, 0), (0, -1), (-1, 0), (0, 0)];
    let mut queue: Vec<Pos> = vec![Pos {
        i: 0,
        j: 1,
        step: 0,
        blizzards,
    }];
    let mut prev: Vec<(i32, i32, Vec<(i32, i32, usize)>)> = Vec::new();
    let mut result = 0;
    while !queue.is_empty() {
        let mut cur = queue.pop().unwrap();
        if (cur.i, cur.j)
            == (
                (grid.len() - 1) as i32,
                (grid[grid.len() - 1].len() - 2) as i32,
            )
        {
            prev = Vec::new();
            queue = vec![Pos {
                i: cur.i,
                j: cur.j,
                step: 0,
                blizzards: cur.blizzards.clone(),
            }];
            result += cur.step;
            break;
        }
        let cur_tuple = (cur.i, cur.j, cur.blizzards.clone());
        if prev.contains(&cur_tuple) {
            continue;
        }
        prev.push(cur_tuple);
        cur.blizzards = cur
            .blizzards
            .iter()
            .map(|(i, j, dir)| {
                let res = (i + dirs[*dir].0, j + dirs[*dir].1, *dir);
                if res.0 <= 0 {
                    ((grid.len() - 2) as i32, res.1, res.2)
                } else if res.0 >= (grid.len() - 1) as i32 {
                    (1, res.1, res.2)
                } else if res.1 <= 0 {
                    (res.0, (grid[res.0 as usize].len() - 2) as i32, res.2)
                } else if res.1 >= (grid[res.0 as usize].len() - 1) as i32 {
                    (res.0, 1, res.2)
                } else {
                    res
                }
            })
            .collect();
        for dir in dirs {
            if !cur.blizzards.contains(&(cur.i + dir.0, cur.j + dir.1, 0))
                && !cur.blizzards.contains(&(cur.i + dir.0, cur.j + dir.1, 1))
                && !cur.blizzards.contains(&(cur.i + dir.0, cur.j + dir.1, 2))
                && !cur.blizzards.contains(&(cur.i + dir.0, cur.j + dir.1, 3))
                && cur.i + dir.0 >= 0
                && cur.i + dir.0 < grid.len() as i32
                && cur.j + dir.1 >= 0
                && cur.j + dir.1 < grid[cur.i as usize].len() as i32
                && grid[(cur.i + dir.0) as usize][(cur.j + dir.1) as usize] != '#'
            {
                queue.insert(
                    0,
                    Pos {
                        i: cur.i + dir.0,
                        j: cur.j + dir.1,
                        step: cur.step + 1,
                        blizzards: cur.blizzards.clone(),
                    },
                );
            }
        }
    }
    while !queue.is_empty() {
        let mut cur = queue.pop().unwrap();
        if (cur.i, cur.j) == (0, 1) {
            prev = Vec::new();
            queue = vec![Pos {
                i: cur.i,
                j: cur.j,
                step: 0,
                blizzards: cur.blizzards.clone(),
            }];
            result += cur.step;
            break;
        }
        let cur_tuple = (cur.i, cur.j, cur.blizzards.clone());
        if prev.contains(&cur_tuple) {
            continue;
        }
        prev.push(cur_tuple);
        cur.blizzards = cur
            .blizzards
            .iter()
            .map(|(i, j, dir)| {
                let res = (i + dirs[*dir].0, j + dirs[*dir].1, *dir);
                if res.0 <= 0 {
                    ((grid.len() - 2) as i32, res.1, res.2)
                } else if res.0 >= (grid.len() - 1) as i32 {
                    (1, res.1, res.2)
                } else if res.1 <= 0 {
                    (res.0, (grid[res.0 as usize].len() - 2) as i32, res.2)
                } else if res.1 >= (grid[res.0 as usize].len() - 1) as i32 {
                    (res.0, 1, res.2)
                } else {
                    res
                }
            })
            .collect();
        for dir in dirs {
            if !cur.blizzards.contains(&(cur.i + dir.0, cur.j + dir.1, 0))
                && !cur.blizzards.contains(&(cur.i + dir.0, cur.j + dir.1, 1))
                && !cur.blizzards.contains(&(cur.i + dir.0, cur.j + dir.1, 2))
                && !cur.blizzards.contains(&(cur.i + dir.0, cur.j + dir.1, 3))
                && cur.i + dir.0 >= 0
                && cur.i + dir.0 < grid.len() as i32
                && cur.j + dir.1 >= 0
                && cur.j + dir.1 < grid[cur.i as usize].len() as i32
                && grid[(cur.i + dir.0) as usize][(cur.j + dir.1) as usize] != '#'
            {
                queue.insert(
                    0,
                    Pos {
                        i: cur.i + dir.0,
                        j: cur.j + dir.1,
                        step: cur.step + 1,
                        blizzards: cur.blizzards.clone(),
                    },
                );
            }
        }
    }
    while !queue.is_empty() {
        let mut cur = queue.pop().unwrap();
        if (cur.i, cur.j)
            == (
                (grid.len() - 1) as i32,
                (grid[grid.len() - 1].len() - 2) as i32,
            )
        {
            result += cur.step;
            break;
        }
        let cur_tuple = (cur.i, cur.j, cur.blizzards.clone());
        if prev.contains(&cur_tuple) {
            continue;
        }
        prev.push(cur_tuple);
        cur.blizzards = cur
            .blizzards
            .iter()
            .map(|(i, j, dir)| {
                let res = (i + dirs[*dir].0, j + dirs[*dir].1, *dir);
                if res.0 <= 0 {
                    ((grid.len() - 2) as i32, res.1, res.2)
                } else if res.0 >= (grid.len() - 1) as i32 {
                    (1, res.1, res.2)
                } else if res.1 <= 0 {
                    (res.0, (grid[res.0 as usize].len() - 2) as i32, res.2)
                } else if res.1 >= (grid[res.0 as usize].len() - 1) as i32 {
                    (res.0, 1, res.2)
                } else {
                    res
                }
            })
            .collect();
        for dir in dirs {
            if !cur.blizzards.contains(&(cur.i + dir.0, cur.j + dir.1, 0))
                && !cur.blizzards.contains(&(cur.i + dir.0, cur.j + dir.1, 1))
                && !cur.blizzards.contains(&(cur.i + dir.0, cur.j + dir.1, 2))
                && !cur.blizzards.contains(&(cur.i + dir.0, cur.j + dir.1, 3))
                && cur.i + dir.0 >= 0
                && cur.i + dir.0 < grid.len() as i32
                && cur.j + dir.1 >= 0
                && cur.j + dir.1 < grid[cur.i as usize].len() as i32
                && grid[(cur.i + dir.0) as usize][(cur.j + dir.1) as usize] != '#'
            {
                queue.insert(
                    0,
                    Pos {
                        i: cur.i + dir.0,
                        j: cur.j + dir.1,
                        step: cur.step + 1,
                        blizzards: cur.blizzards.clone(),
                    },
                );
            }
        }
    }
    result
}

fn part1(data: &str) -> usize {
    let grid: Vec<Vec<char>> = data.lines().map(|i| i.chars().collect()).collect();
    let mut blizzards: Vec<(i32, i32, usize)> = Vec::new();
    for (idx, i) in data.lines().enumerate() {
        blizzards.append(
            &mut i
                .chars()
                .enumerate()
                .filter(|(_, i)| ">v<^".contains(*i))
                .map(|(jdx, j)| (idx as i32, jdx as i32, ">v<^".find(j).unwrap()))
                .collect(),
        );
    }
    let dirs = [(0, 1), (1, 0), (0, -1), (-1, 0), (0, 0)];
    let mut queue: Vec<Pos> = vec![Pos {
        i: 0,
        j: 1,
        step: 0,
        blizzards,
    }];
    let mut prev: Vec<(i32, i32, Vec<(i32, i32, usize)>)> = Vec::new();
    while !queue.is_empty() {
        let mut cur = queue.pop().unwrap();
        if (cur.i, cur.j)
            == (
                (grid.len() - 1) as i32,
                (grid[grid.len() - 1].len() - 2) as i32,
            )
        {
            return cur.step;
        }
        let cur_tuple = (cur.i, cur.j, cur.blizzards.clone());
        if prev.contains(&cur_tuple) {
            continue;
        }
        prev.push(cur_tuple);
        cur.blizzards = cur
            .blizzards
            .iter()
            .map(|(i, j, dir)| {
                let res = (i + dirs[*dir].0, j + dirs[*dir].1, *dir);
                if res.0 <= 0 {
                    ((grid.len() - 2) as i32, res.1, res.2)
                } else if res.0 >= (grid.len() - 1) as i32 {
                    (1, res.1, res.2)
                } else if res.1 <= 0 {
                    (res.0, (grid[res.0 as usize].len() - 2) as i32, res.2)
                } else if res.1 >= (grid[res.0 as usize].len() - 1) as i32 {
                    (res.0, 1, res.2)
                } else {
                    res
                }
            })
            .collect();
        for dir in dirs {
            if !cur.blizzards.contains(&(cur.i + dir.0, cur.j + dir.1, 0))
                && !cur.blizzards.contains(&(cur.i + dir.0, cur.j + dir.1, 1))
                && !cur.blizzards.contains(&(cur.i + dir.0, cur.j + dir.1, 2))
                && !cur.blizzards.contains(&(cur.i + dir.0, cur.j + dir.1, 3))
                && cur.i + dir.0 >= 0
                && cur.i + dir.0 < grid.len() as i32
                && cur.j + dir.1 >= 0
                && cur.j + dir.1 < grid[cur.i as usize].len() as i32
                && grid[(cur.i + dir.0) as usize][(cur.j + dir.1) as usize] != '#'
            {
                queue.insert(
                    0,
                    Pos {
                        i: cur.i + dir.0,
                        j: cur.j + dir.1,
                        step: cur.step + 1,
                        blizzards: cur.blizzards.clone(),
                    },
                );
            }
        }
    }
    0
}

fn main() {
    let data = fs::read_to_string("day24.txt").unwrap();
    println!("{}\n{}", part1(&data), part2(&data));
}
